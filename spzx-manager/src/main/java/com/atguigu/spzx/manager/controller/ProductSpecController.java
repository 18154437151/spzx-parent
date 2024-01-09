package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/system/productSpec")
@Tag(name = "商品规格管理")  // controller类的描述
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    @Operation(summary = "规格分页查询")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageInfo pageInfo = productSpecService.findByPage(pageNum,pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "规格添加")
    @PostMapping("/add")
    public Result add(@RequestBody ProductSpec productSpec){
        productSpecService.add(productSpec);
        return Result.ok(null);
    }
    // 删除，修改
}
