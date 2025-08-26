package com.open.api.web.controller;

import com.open.api.config.gateway.GateWayService;
import com.open.api.model.ResultModel;
import com.open.api.web.bo.GateWayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 统一网关
 */
@Slf4j
@RestController
@RequestMapping("/open")
public class OpenApiController {
    @Autowired
    private GateWayService gateWayService;


    /**
     * 统一网关入口
     */
    @PostMapping("/gateway")
    public ResultModel gateway(@Validated @RequestBody GateWayRequest gateWayRequest) {
        log.info("gateway request:{}",gateWayRequest);
        //请求接口
        ResultModel result = gateWayService.invoke(gateWayRequest);
        log.info("gateway result:{}",result);
        return result;
    }
}
