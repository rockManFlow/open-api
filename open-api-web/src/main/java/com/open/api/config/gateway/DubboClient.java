package com.open.api.config.gateway;

import com.open.api.enums.MethodTypeEnum;

/**
 * @author rock
 * @detail
 * @date 2025/8/26 11:49
 */
public class DubboClient extends OpenClient {
    public DubboClient() {
        super(MethodTypeEnum.DUBBO);
    }

    @Override
    public String invoke(String uri, String context, String requestId) throws Exception {
        return null;
    }
}
