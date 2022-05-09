package top.nzhz.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/*
* http消息转换器 默认Jackson
*
* */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
