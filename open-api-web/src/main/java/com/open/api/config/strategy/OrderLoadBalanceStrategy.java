package com.open.api.config.strategy;

import com.open.api.enums.ApiExceptionEnum;
import com.open.api.enums.StrategyEnum;
import com.open.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author rock
 * @detail
 * @date 2025/8/26 16:57
 */
@Component
@Slf4j
public class OrderLoadBalanceStrategy extends LoadBalanceStrategy {
    private static final int max_num=10000;
    private AtomicInteger count;
    public OrderLoadBalanceStrategy() {
        super(StrategyEnum.ORDER);
        count=new AtomicInteger(0);
    }

    @Override
    public String getLoadNode(Map<String, Integer> ipWeightMap) {
        if(MapUtils.isEmpty(ipWeightMap)){
            throw new BusinessException(ApiExceptionEnum.NO_AVAILABLE_SERVICE);
        }
        String[] ipArray = ipWeightMap.keySet().toArray(new String[]{});
        int index=count.getAndIncrement()%ipArray.length;

        //当累计到一定值时，重新计数。防止一直累加导致数过大，占用更多内存
        if(count.get()>max_num){
            count.set(0);
        }
        return ipArray[index];
    }
}
