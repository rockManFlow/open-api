package com.open.api.config.strategy;

import com.open.api.enums.StrategyEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rock
 * @detail StrategyEnum
 * @date 2025/8/26 16:06
 */
public abstract class LoadBalanceStrategy {
    private static final Map<String,LoadBalanceStrategy> strategyMap=new HashMap<>(4);

    public LoadBalanceStrategy(StrategyEnum strategyEnum){
        strategyMap.put(strategyEnum.name(), this);
    }

    public static LoadBalanceStrategy getLoadBalanceStrategy(String strategy){
        return strategyMap.get(strategy);
    }

    abstract public String getLoadNode(Map<String,Integer> ipWeightMap);
}
