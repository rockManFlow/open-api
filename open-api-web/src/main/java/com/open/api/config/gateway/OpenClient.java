package com.open.api.config.gateway;

import com.open.api.enums.MethodTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rock
 * @detail
 * @date 2025/8/25 16:08
 */
public abstract class OpenClient {
    private static final Map<String,OpenClient> clientMap=new HashMap<>(4);

    OpenClient(MethodTypeEnum methodTypeEnum){
        clientMap.put(methodTypeEnum.name(),this);
    }

    public static OpenClient getOpenClient(String method){
        return clientMap.get(method);
    }

    abstract public String invoke(String uri,String context,String requestId) throws Exception;
}
