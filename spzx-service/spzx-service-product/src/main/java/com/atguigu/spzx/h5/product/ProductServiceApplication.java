package com.atguigu.spzx.h5.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.atguigu.spzx.common.swagger","com.atguigu.spzx.common.exp","com.atguigu.spzx.common.cors","com.atguigu.spzx.h5.product"})
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class,args);
    }
}
