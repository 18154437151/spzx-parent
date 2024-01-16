package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/product/brand")
@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("/findAll")
    public Result getBrandList(){
        List<Brand> list = brandService.findAll();
        return Result.ok(list);

    }
}
