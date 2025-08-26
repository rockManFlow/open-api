package com.open.api.config.strategy;

import com.open.api.enums.ApiExceptionEnum;
import com.open.api.enums.StrategyEnum;
import com.open.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rock
 * @detail
 * @date 2025/8/26 16:56
 */
@Slf4j
@Component
public class WeightLoadBalanceStrategy extends LoadBalanceStrategy {
    public WeightLoadBalanceStrategy() {
        super(StrategyEnum.WEIGHT);
    }

    //权重
    @Override
    public String getLoadNode(Map<String,Integer> ipWeightMap){
        if(MapUtils.isEmpty(ipWeightMap)){
            throw new BusinessException(ApiExceptionEnum.NO_AVAILABLE_SERVICE);
        }
        //计算总值
        Integer sumCount =ipWeightMap.values().stream().mapToInt(a->a).sum();

        //产生随机数
        int offset = ThreadLocalRandom.current().nextInt(sumCount);
        Integer currentCount=0;
        Set<String> keys = ipWeightMap.keySet();
        for(String k:keys){
            currentCount+=ipWeightMap.get(k);
            if(offset<=currentCount){
                return k;
            }
        }
        throw new BusinessException(ApiExceptionEnum.NO_AVAILABLE_SERVICE);
    }
}
