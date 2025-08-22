package com.open.api.util;

import com.open.api.web.bo.BaseRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 10:55
 */
@Slf4j
public class AccessLogUtil {
    public static void writeAccessLog(String uri, BaseRequest baseRequest,long cost){
        if(baseRequest!=null){
            log.info("uri:{},appId:{},method:{},version:{},requestId:{},signType:{},cost:{}ms",uri,baseRequest.getAppId(),baseRequest.getMethod(),baseRequest.getVersion(),baseRequest.getRequestId(),baseRequest.getSignType(),cost);
            return;
        }
        log.info("uri:{},cost:{}ms",uri,cost);
    }
}
