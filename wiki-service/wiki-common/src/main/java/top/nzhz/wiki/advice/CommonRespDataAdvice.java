package top.nzhz.wiki.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.nzhz.wiki.annotation.IgnoreRespAdvice;
import top.nzhz.wiki.value.object.CommonResp;

/**
 * @Class: CommonRespDataAdvice
 * @Description: 统一响应处理
 * @Author: Xisha
 * @Date: 2022/05/17 00:41
 **/

@RestControllerAdvice
public class CommonRespDataAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //如果当前方法所在类标识了@IgnoreRespAdvice，则无需处理
        if(returnType.getDeclaringClass().isAnnotationPresent(IgnoreRespAdvice.class)){
            return false;
        }
        //如果当前方法标识了@IgnoreRespAdvice，则无需处理
        if(returnType.getAnnotatedElement().isAnnotationPresent(IgnoreRespAdvice.class)){
            return false;
        }
        //其余情况需要处理，执行beforeBodyWrite()
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //定义最终返回对象
        CommonResp<Object> commonResp = new CommonResp<>();
        if(body==null){
            return commonResp;
        } else if(body instanceof CommonResp){
            commonResp=(CommonResp<Object>) body;
        } else {
            commonResp.setContent(body);
        }
        return commonResp;
    }
}
