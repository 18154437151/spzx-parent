package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        // 一级分类列表
        List<Category> list = categoryService.findCategoryTree();
        return Result.ok(list);
    }

}
