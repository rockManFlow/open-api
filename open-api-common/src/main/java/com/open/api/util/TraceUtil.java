package com.open.api.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 11:14
 */
public class TraceUtil {
    private static final String traceId="traceId";

    public static String genTraceId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void setTraceId(){
        setTraceId(genTraceId());
    }
    public static void setTraceId(String tid){
        MDC.put(traceId,tid);
    }

    public static void removeTraceId(){
        MDC.remove(traceId);
    }
}
