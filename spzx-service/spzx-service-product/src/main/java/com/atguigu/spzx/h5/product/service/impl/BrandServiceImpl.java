package com.atguigu.spzx.h5.product.service.impl;

import com.atguigu.spzx.h5.product.mapper.IndexMapper;
import com.atguigu.spzx.h5.product.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<Brand> findAll() {
        return indexMapper.findAllBrand();
    }
}
