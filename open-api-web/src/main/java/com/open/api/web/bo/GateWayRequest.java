package com.open.api.web.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 9:52
 */
@Data
public class GateWayRequest extends BaseRequest{
    /**
     * 签名：私钥加密（公共参数+随机秘钥串）
     */
    @NotBlank
    private String sign;

    /**
     * 根据随机秘钥串对称解密报文
     * 加密后的业务内容 ：json 格式字符串
     */
    private String content;
}
