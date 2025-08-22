package com.open.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.open.api.config.gateway.ApiClient;
import com.open.api.model.ResultModel;
import com.open.api.util.SystemClock;
import com.open.api.web.bo.GateWayRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一网关
 */
@Slf4j
@RestController
@RequestMapping("/open")
public class OpenApiController {
    @Autowired
    private ApiClient apiClient;


    /**
     * 统一网关入口
     */
    @PostMapping("/gateway")
    public ResultModel gateway(@Validated @RequestBody GateWayRequest gateWayRequest) throws Throwable {
        //验签
        apiClient.checkSign(params, apiRequestId, charset, signType);

        //请求接口
        ResultModel result = apiClient.invoke(method, apiRequestId, content);

        return result;
    }
}
