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
    private static final Map<String,OpenSignature> signMap=new HashMap<>(8);
    protected OpenSignature(SignTypeEnum typeEnum){
        signMap.put(typeEnum.name(),this);
    }

    public static OpenSignature getOpenSignature(String type){
        return signMap.get(type);
    }
    abstract public boolean verifySign(String appId,String publicKey,T t);

    abstract public String encryptResult(String context, String secretKey) throws Exception;
}
