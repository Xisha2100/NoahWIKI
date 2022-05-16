package top.nzhz.wiki.gateway.filter;

import org.springframework.stereotype.Component;

/**
 * @Class: PreRequestFilter
 * @Description:存储客户发起请求时的时间戳
 * @Author: Xisha
 * @Date: 2022/05/16 20:01
 **/

@Component
public class PreRequestFilter extends AbstractPreZuulFilter{
    @Override
    protected Object cRun() {
        requestContext.set("startTime",System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
