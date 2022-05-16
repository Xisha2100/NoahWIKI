package top.nzhz.wiki.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Class: IgnoreRespAdvice
 * @Description: 忽略统一相应注解
 * @Author: Xisha
 * @Date: 2022/05/16 21:35
 **/

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreRespAdvice {

}
