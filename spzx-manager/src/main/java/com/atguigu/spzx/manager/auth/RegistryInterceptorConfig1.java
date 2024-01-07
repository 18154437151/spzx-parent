/*
package com.atguigu.spzx.manager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class RegistryInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private NoAuthPathList noAuthPathList;
    @Autowired
    private UserLoginAuthInterceptor loginAuthInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(noAuthPathList.getList())
                .addPathPatterns("/**");
    }
}
*/
