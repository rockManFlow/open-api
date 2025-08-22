package com.open.api.config.sign;

import com.open.api.enums.SignTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:16
 */
public abstract class OpenSignature<T> {
    private static final Map<SignTypeEnum,OpenSignature> signMap=new HashMap<>(8);
    protected OpenSignature(SignTypeEnum typeEnum){
        signMap.put(typeEnum,this);
    }

    abstract boolean verifySign(String appId,String publicKey,T t);
}
