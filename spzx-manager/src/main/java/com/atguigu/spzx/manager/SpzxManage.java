package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.auth.NoAuthPathList;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
@MapperScan("com.atguigu.spzx.manager.mapper")
@ComponentScan(basePackages = "com.atguigu")
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@EnableConfigurationProperties(value = {NoAuthPathList.class, MinioProperties.class})
public class SpzxManage {
    public static void main(String[] args) {
        SpringApplication.run(SpzxManage.class,args);
    }
}
