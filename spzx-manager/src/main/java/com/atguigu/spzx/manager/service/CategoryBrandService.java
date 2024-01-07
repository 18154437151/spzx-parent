package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);

    void addCategoryBrand(CategoryBrandDto categoryBrandDto);

    void deleteById(Long id);

    List<Long> getIdList(Long threeId);

    void updateCategoryBrand(CategoryBrand categoryBrand);
}
