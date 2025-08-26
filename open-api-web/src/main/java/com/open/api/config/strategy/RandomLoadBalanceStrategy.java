package com.open.api.config.strategy;

import com.open.api.enums.ApiExceptionEnum;
import com.open.api.enums.StrategyEnum;
import com.open.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author rock
 * @detail
 * @date 2025/8/26 16:56
 */
@Slf4j
@Component
public class RandomLoadBalanceStrategy extends LoadBalanceStrategy {
    public RandomLoadBalanceStrategy() {
        super(StrategyEnum.RANDOM);
    }

    /**
     * 随机路由
     * @param ipWeightMap
     * @return
     */
    @Override
    public String getLoadNode(Map<String,Integer> ipWeightMap) {
        if(MapUtils.isEmpty(ipWeightMap)){
            throw new BusinessException(ApiExceptionEnum.NO_AVAILABLE_SERVICE);
        }
        Set<String> ipSet=ipWeightMap.keySet();
        if(ipSet.isEmpty()) return "";
        Object[] array = ipSet.toArray();
        int randomIndex = new Random().nextInt(array.length);
        return (String) array[randomIndex];
    }
}
