package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductSpec> list = productSpecMapper.findList();
        return new PageInfo(list);
    }

    @Override
    public void add(ProductSpec productSpec) {
        productSpecMapper.add(productSpec);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findList();
    }
}
