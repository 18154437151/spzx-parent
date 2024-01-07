package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/brand")
@CrossOrigin
@Tag(name = "品牌管理")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Operation(summary = "品牌列表")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageInfo pageInfo = brandService.findByPage(pageNum,pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    @PostMapping("/addBrand")
    public Result addBrand(@RequestBody Brand brand){
        brandService.addBrand(brand);
        // return Result.build(null,ResultCodeEnum.SUCCESS);
        return Result.ok(null);
    }
    @PostMapping("/updateBrand")
    public Result updateBrand(@RequestBody Brand brand){
        brandService.updateBrand(brand);
        // return Result.build(null,ResultCodeEnum.SUCCESS);
        return Result.ok(null);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        brandService.deleteById(id);
        return Result.ok(null);
    }

    @Operation(summary = "查询所有的品牌（前端下拉框）")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.ok(list);
    }
}
