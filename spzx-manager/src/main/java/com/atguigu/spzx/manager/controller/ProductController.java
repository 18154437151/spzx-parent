package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/system/product")
@Tag(name = "商品管理")  // controller类的描述
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody(required = false) ProductDto productDto){
        PageInfo pageInfo = productService.findByPage(pageNum,pageSize,productDto);
        return Result.ok(pageInfo);
    }

    @Operation(summary = "修改时用于商品信息的回显")
    @GetMapping("/getByProductId/{productId}")
    public Result getByProductId(@PathVariable Long productId){
        Product product = productService.getByProductId(productId);
        return Result.ok(product);
    }

    @DeleteMapping("/deleteByProductId/{productId}")
    @Operation(summary = "删除商品")
    public Result deleteByProductId(@PathVariable Long productId){
        productService.deleteById(productId);
        return Result.ok(null);
    }
}
