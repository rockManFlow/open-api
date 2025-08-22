package com.open.api.enums;

import lombok.Getter;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:08
 */
@Getter
public enum SignTypeEnum {
    RSA("单独RSA方式加签验签，报文明文"),RSA_AES("RSA私钥加签，AES加密报文"),AES("AES加签，报文明文");
    private String desc;
    SignTypeEnum(String desc){
        this.desc=desc;
    }
}
