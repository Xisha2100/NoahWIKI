package top.nzhz.wiki.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class: AccessLogFilter
 * @Description:
 * @Author: Xisha
 * @Date: 2022/05/16 21:11
 **/

@Component
public class AccessLogFilter extends AbstractPostZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AccessLogFilter.class);
    @Override
    protected Object cRun() {
        HttpServletRequest request = requestContext.getRequest();
        long startTime = (long) requestContext.get("startTime");
        String uri=request.getRequestURI();
        long duration=System.currentTimeMillis()-startTime;

        LOG.info("uri:{}, duration:{}",uri,duration);
        return success();
    }

    @Override
    public int filterOrder() {
        //大于该常数不会执行
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
