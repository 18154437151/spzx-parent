package com.atguigu.spzx.manager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegistryInterceptorConfig  implements WebMvcConfigurer {

    @Autowired
    UserLoginAuthInterceptor userLoginAuthInterceptor;

    @Autowired
    NoAuthPathList noAuthPathList;


    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .excludePathPatterns(noAuthPathList.getList())
//                .excludePathPatterns(
//                        "/admin/system/index/login",
//                        "/admin/system/index/generateValidateCode")
                .addPathPatterns("/**");
    }
}
