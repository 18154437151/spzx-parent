package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/categoryBrand")
@CrossOrigin
@Tag(name = "分类品牌管理")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "查询分类品牌列表")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(Integer pageNum, Integer pageSize, @RequestBody CategoryBrandDto categoryBrandDto){
        PageInfo pageInfo = categoryBrandService.findByPage(pageNum,pageSize,categoryBrandDto);
        return Result.ok(pageInfo);
    }
}
