package com.atguigu.spzx.common.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 跨域的公共的配置类
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // 重写addCorsMappings方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 所有的方法都支持跨域
                .allowedHeaders("*")  // 允许携带所有的请求头，如果写成abc,表示只允许请求头中有一个abc,如果还有其他的请求头，则不再支持跨域
                .allowCredentials(true)  // 是否允许携带cookie
                .allowedMethods("*")  // 无论请求的类型是get或者post,put,delete...都允许跨域
                .allowedOrigins("*");  // 允许前端所有的域
    }
}
