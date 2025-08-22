package com.open.api.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 11:30
 */
@Data
@Builder
public class LogModel {
    /**
     * 当前请求uri
     */
    private String uri;
    /**
     * 业务方appId
     */
    @NotBlank
    private String appId;
    /**
     * 请求方法
     */
    @NotBlank
    private String method;
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
     * 签名类型：RSA
     */
    @NotBlank
    private String signType;

    /**
     * 耗时ms
     */
    private long cost;
}
