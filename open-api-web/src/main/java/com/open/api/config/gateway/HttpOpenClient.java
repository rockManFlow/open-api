package com.open.api.config.gateway;

import com.open.api.enums.MethodTypeEnum;
import com.open.api.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rock
 * @detail
 * @date 2025/8/25 17:18
 */
@Service
@Slf4j
public class HttpOpenClient extends OpenClient {
    public HttpOpenClient() {
        super(MethodTypeEnum.HTTP);
    }

    @Override
    public String invoke(String uri, String context, String requestId) throws Exception {
        Map<String,String> header=new HashMap<>(2);
        header.put("requestId",requestId);
        return HttpUtils.httpPost(uri,context,header);
    }
}
