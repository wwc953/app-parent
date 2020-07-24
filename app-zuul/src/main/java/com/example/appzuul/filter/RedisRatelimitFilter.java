package com.example.appzuul.filter;

import com.example.appzuul.ratelimit.RedisSlidingWindowRatelimit;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/24 8:47
 */
@Slf4j
@Component
public class RedisRatelimitFilter extends ZuulFilter {

    @Autowired
    RedisSlidingWindowRatelimit redisSlidingWindowRatelimit;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String remoteAddr = request.getRemoteAddr();

        boolean redisRatelimit = redisSlidingWindowRatelimit.redisRatelimit(remoteAddr, 2000L, 5);

        if (!redisRatelimit){
            throw new RuntimeException("已被限制！！！！请稍后重试");
        }
        return null;
    }


}
