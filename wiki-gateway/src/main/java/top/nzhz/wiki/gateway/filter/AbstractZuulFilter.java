package top.nzhz.wiki.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public abstract class AbstractZuulFilter extends ZuulFilter {

    //于过滤器间传递消息，保存于ThreadLocal
    RequestContext requestContext;

    //标记是否需要继续往下执行
    private final String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {
        requestContext = RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code, String msg) {
        requestContext.set(NEXT, false);
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseBody(String.format("{\"result\":\"%s!\"}", msg));
        return null;
    }

    Object success() {
        requestContext.set(NEXT, true);
        return null;
    }
}
