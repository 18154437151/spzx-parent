package com.atguigu.spzx.manager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class RegistryInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private NoAuthPathList noAuthPathList;
    @Autowired
    private UserLoginAuthInterceptor loginAuthInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
//                .excludePathPatterns(
//                        "/admin/system/index/login",
//                        "/admin/system/index/generateValidateCode"
//                )
                .excludePathPatterns(noAuthPathList.getList())
                .addPathPatterns("/**");
    }
}
