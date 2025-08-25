package com.open.api.enums;

import lombok.Getter;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:08
 */
@Getter
public enum MethodTypeEnum {
    HTTP("HTTP"),DUBBO("DUBBO");
    private String desc;
    MethodTypeEnum(String desc){
        this.desc=desc;
    }
}
