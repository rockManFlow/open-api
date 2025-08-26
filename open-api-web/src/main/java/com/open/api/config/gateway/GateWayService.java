package com.open.api.config.gateway;

import com.open.api.config.property.ApplicationProperty;
import com.open.api.config.sign.OpenSignature;
import com.open.api.config.strategy.LoadBalanceStrategy;
import com.open.api.enums.ApiExceptionEnum;
import com.open.api.enums.StrategyEnum;
import com.open.api.exception.BusinessException;
import com.open.api.model.AppIdModel;
import com.open.api.model.ResultModel;
import com.open.api.web.bo.GateWayRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * Api请求客户端
 */
@Slf4j
@Service
public class GateWayService {
    @Resource
    private ApplicationProperty applicationProperty;

    /**
     * 验签
     */
    public OpenSignature checkSign(GateWayRequest gateWayRequest) {
        try {
            Map<String, AppIdModel> appKeyMap = applicationProperty.getAppKeyMap();
            AppIdModel appIdModel = appKeyMap.get(gateWayRequest.getAppId());
            if(appIdModel==null){
                log.error("Not Exist appId:{}",gateWayRequest.getAppId());
                throw new BusinessException(ApiExceptionEnum.INVALID_SIGN);
            }
            OpenSignature openSignature = OpenSignature.getOpenSignature(appIdModel.getSignType());
            if(openSignature==null){
                log.error("Not Support SignType:{}",appIdModel.getSignType());
                throw new BusinessException(ApiExceptionEnum.NOT_SUPPORT_SIGN_TYPE);
            }

            boolean checkSign = openSignature.verifySign(gateWayRequest.getAppId(),appIdModel.getPublicKey(),gateWayRequest);;
            if (!checkSign) {
                log.info("验签失败 appId:{},param:{}",gateWayRequest.getAppId(),gateWayRequest);
                throw new BusinessException(ApiExceptionEnum.INVALID_SIGN);
            }
            log.info("验签成功 appId:{}",gateWayRequest.getAppId());
            return openSignature;

        } catch (Exception e) {
            log.error("验签异常 params = {}",gateWayRequest);
            throw new BusinessException(ApiExceptionEnum.INVALID_SIGN);
        }
    }



    /**
     * Api调用方法
     */
    public ResultModel invoke(GateWayRequest gateWayRequest) {
        //验签
        OpenSignature openSignature=checkSign(gateWayRequest);

        //获取api方法
        Map<String, AppIdModel> appKeyMap = applicationProperty.getAppKeyMap();
        AppIdModel appIdModel = appKeyMap.get(gateWayRequest.getAppId());

        if (null == appIdModel) {
            log.info("API 信息不存在 appId:{}", gateWayRequest.getAppId());
            throw new BusinessException(ApiExceptionEnum.API_NOT_EXIST);
        }

        //负载均衡
        LoadBalanceStrategy loadBalanceStrategy = LoadBalanceStrategy.getLoadBalanceStrategy(StrategyEnum.getStrategyEnum(appIdModel.getLoadBalanceStrategy()).name());
        Map<String,Integer> ipWeightMap=new HashMap<>(4);
        Iterator<String> iterator = appIdModel.getRemoteInfo().iterator();
        int index=0;
        while (iterator.hasNext()){
            ipWeightMap.put(iterator.next(),getWeight(appIdModel.getWeight(),index));
            index++;
        }

        //执行对应方法
        try {
            StringBuilder uri=new StringBuilder("http://").append(loadBalanceStrategy.getLoadNode(ipWeightMap)).append("/").append(gateWayRequest.getUri());
            String resp = OpenClient.getOpenClient(gateWayRequest.getMethod()).invoke(uri.toString(),gateWayRequest.getContent(),gateWayRequest.getRequestId());
            return ResultModel.success(openSignature.encryptResult(resp,gateWayRequest.getRandomAesKey()));
        } catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            log.error("invoke error",e);
            throw new BusinessException(ApiExceptionEnum.SYSTEM_ERROR);
        }
    }

    private Integer getWeight(List<Integer> weight,int index){
        if(CollectionUtils.isEmpty(weight)||weight.size()<=index){
            return 0;
        }
        return weight.get(index);
    }
}
