package com.open.api.model;

import lombok.Data;

import java.util.Set;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 18:48
 */
@Data
public class AppIdModel {
    private String signType;
    /**
     * 验签公钥
     */
    private String publicKey;

    /**
     * ip1:port1,ip2:port2,ip3:port3
     */
    private Set<String> remoteInfo;
}
