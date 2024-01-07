package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysCategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Tag(name = "商品分类相关接口")  // controller类的描述
@RestController
@RequestMapping("/admin/system/category")
public class SysCategoryController {
    @Autowired
    private SysCategoryService sysCategoryService;

    @Operation(summary = "根据parentId查询下级商品分类列表")
    @GetMapping("/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        List<Category> list = sysCategoryService.findByParentId(parentId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "导出excel文档")
    @GetMapping("/download")
    public void download(HttpServletResponse httpServletResponse) throws IOException {
        sysCategoryService.download(httpServletResponse);
    }

    @Operation(summary = "导入excel文档")
    @PostMapping("/importExcel")
    public Result importExcel(MultipartFile file) throws IOException {
        sysCategoryService.importExcel(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
