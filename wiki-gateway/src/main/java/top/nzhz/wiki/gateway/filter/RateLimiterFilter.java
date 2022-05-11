package top.nzhz.wiki.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: RateLimiterFilter
 * @Description: 限流器
 * @Author: xisha
 * @Date: 2022/5/11 15:14
 */

@Component
public class RateLimiterFilter extends AbstractPreZuulFilter{

    private static final Logger LOG = LoggerFactory.getLogger(RateLimiterFilter.class);

    //每秒两个令牌
    RateLimiter rateLimiter=RateLimiter.create(2.0);

    @Override
    protected Object cRun() {
        HttpServletRequest request=requestContext.getRequest();
        if(rateLimiter.tryAcquire()){
            LOG.info("Get rate token success");
            return success();
        }else {
            LOG.error("Rate limit:{}",request.getRequestURI());
            return fail(402,"error: Rate Limit ");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
