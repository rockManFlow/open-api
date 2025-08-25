package com.open.api.web.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rock
 * @detail 加签消息体
 * @date 2025/8/25 10:07
 */
@Data
public class SignInfo {
    /**
     * 请求方法 HTTP
     */
    @NotBlank
    private String method;

    /**
     * 请求路径
     */
    @NotBlank
    private String uri;

    /**
     * 版本1.0
     */
    @NotBlank
    private String version;
    /**
     * 随机请求标识，用于区分每一次请求
     */
    @NotBlank
    private String requestId;
    /**
     * 签名类型：RSA、RSA+AES、AES
     */
    @NotBlank
    private String signType;

    /**
     * 随机aes key
     * 非必填，仅验签方式是rsa+aes是必填
     */
    private String randomAesKey;
}
