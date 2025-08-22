package com.open.api.config.advice;

import com.open.api.config.context.CachedBodyHttpServletRequest;
import com.open.api.util.AccessLogUtil;
import com.open.api.util.SystemClock;
import com.open.api.util.TraceUtil;
import com.open.api.web.bo.BaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author rock
 * @detail
 * @date 2025/8/22 11:12
 */
@Slf4j
@Component
public class SystemFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        TraceUtil.setTraceId();
        long start = SystemClock.millisClock().now();
        HttpServletRequest wrappedRequest =null;
        try{
            wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) servletRequest);
            filterChain.doFilter(wrappedRequest, servletResponse);
        }catch (Throwable t){
            log.error("filter error",t);
        }finally {
            long cost=(SystemClock.millisClock().now() - start);
            if(wrappedRequest!=null){
                BaseRequest baseRequest = buildBaseRequest(wrappedRequest);
                String uri=wrappedRequest.getRequestURI();
                AccessLogUtil.writeAccessLog(uri,baseRequest,cost);
            }

            TraceUtil.removeTraceId();
        }
    }

    private BaseRequest buildBaseRequest(HttpServletRequest wrappedRequest){
        try {
            Map<String, Object> params = WebUtils.getParametersStartingWith(wrappedRequest, "");
            BaseRequest baseRequest = new BaseRequest();
            BeanUtils.populate(baseRequest, params);
            return baseRequest;
        }catch (Throwable t){
            log.info("SystemFilter buildBaseRequest error",t);
        }
        return null;
    }
}
