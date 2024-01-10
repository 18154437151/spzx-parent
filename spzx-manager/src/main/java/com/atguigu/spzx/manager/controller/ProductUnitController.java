package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/system/productUnit")
@Tag(name = "计量单位管理")  // controller类的描述
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;
    @GetMapping("/findAll")
    public Result finAll(){
        List<ProductUnit> list = productUnitService.findAll();
        return Result.ok(list);
    }
}
