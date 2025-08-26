package com.open.api.enums;

import lombok.Getter;
/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:08
 */
@Getter
public enum StrategyEnum {
    RANDOM("随机"),WEIGHT("权重"),ORDER("顺序");
    private String desc;
    StrategyEnum(String desc){
        this.desc=desc;
    }

    public static StrategyEnum getStrategyEnum(String name){
        for(StrategyEnum strategyEnum:StrategyEnum.values()){
            if(strategyEnum.name().equalsIgnoreCase(name)){
                return strategyEnum;
            }
        }
        return StrategyEnum.RANDOM;
    }
}
