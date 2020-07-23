package com.example.appzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 自定义过滤器
 * @author: wangwc
 * @date: 2020/7/23 10:05
 */
@Component
public class LogFilter extends ZuulFilter {
    /**
     * 过滤器的类型。有 pre、 route、 post、 error等几种取值
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    /**
     *  filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * 是否要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("访问者IP：" + remoteAddr + "访问地址:" + request.getRequestURI());
        return null;
    }
}
