package top.nzhz.wiki.gateway.filter;

/*
 * 校验请求中传递的Token
 * */

import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends AbstractPreZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    protected Object cRun() {
        HttpServletRequest request = requestContext.getRequest();
        LOG.info(String.format("%s request to %s",
                request.getMethod(), request.getRequestURI()));

        Object token = request.getParameter("token");
        if (token == null) {
            LOG.error("error: token is empty.");
            return fail(401,"当前用户无权限");
        }

        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
