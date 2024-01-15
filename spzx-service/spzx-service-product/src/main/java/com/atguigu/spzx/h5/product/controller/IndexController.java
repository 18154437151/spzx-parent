package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.IndexService;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/product/index")
@Tag(name = "首页接口管理")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @Operation(summary = "首页商品列表和分类列表")
    @GetMapping
    public Result index(){
        Map map = indexService.getMap();
        return Result.ok(map);
    }
}
