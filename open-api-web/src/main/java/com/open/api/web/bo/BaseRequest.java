package com.open.api.web.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 11:39
 */
@Data
public class BaseRequest {
    /**
     * 业务方appId
     */
    @NotBlank
    private String appId;
    /**
     * 请求方法 HTTP
     */
    private String method;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * 版本1.0
     */
    private String version;
    /**
     * 随机请求标识，用于区分每一次请求
     */
    private String requestId;
    /**
     * 签名类型：RSA、RSA+AES、AES
     */
    private String signType;

    /**
     * 随机aes key
     * 非必填，仅验签方式是rsa+aes是必填
     */
    private String randomAesKey;
}
