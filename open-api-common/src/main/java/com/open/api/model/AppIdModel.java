package com.open.api.model;

import lombok.Data;

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
}
