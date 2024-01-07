package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addCategoryBrand")
    @Operation(summary = "建立品牌和分类的关联数据")
    public Result addCategoryBrand(@RequestBody CategoryBrandDto categoryBrandDto){
        categoryBrandService.addCategoryBrand(categoryBrandDto);
        return Result.ok(null);
    }

    @Operation(summary = "删除品牌和分类的关联数据")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        categoryBrandService.deleteById(id);
        return Result.ok(null);
    }

    @Operation(summary = "根据第三级分类id查询id集合")
    @GetMapping("/getIdList/{threeId}")
    public Result getIdList(@PathVariable Long threeId){
        List<Long> idList = categoryBrandService.getIdList(threeId);
        return Result.ok(idList);
    }

    @Operation(summary = "修改当前品牌分类关系数据")
    @PostMapping("/updateCategoryBrand")
    public Result updateCategoryBrand(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateCategoryBrand(categoryBrand);
        return Result.ok(null);
    }
}
