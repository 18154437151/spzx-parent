package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    PageInfo findByPage(Integer pageNum, Integer pageSize);

    void addBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();
}
