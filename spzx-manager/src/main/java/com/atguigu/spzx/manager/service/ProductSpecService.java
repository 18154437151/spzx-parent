package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

public interface ProductSpecService {
    PageInfo findByPage(Integer pageNum, Integer pageSize);

    void add(ProductSpec productSpec);
}
