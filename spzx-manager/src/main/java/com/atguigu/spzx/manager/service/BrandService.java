package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

public interface BrandService {
    PageInfo findByPage(Integer pageNum, Integer pageSize);

    void addBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteById(Long id);
}
