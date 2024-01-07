package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.github.pagehelper.PageInfo;

public interface CategoryBrandService {
    PageInfo findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);
}
