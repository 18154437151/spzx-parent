package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.auth.NoAuthPathList;
import com.atguigu.spzx.manager.properties.MinioProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
// @EnableLogAspect
@EnableConfigurationProperties(value = {NoAuthPathList.class, MinioProperties.class})
@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@MapperScan("com.atguigu.spzx.manager.mapper")
@EnableScheduling
public class SpzxManage {
    public static void main(String[] args) {
        SpringApplication.run(SpzxManage.class,args);
    }
}
