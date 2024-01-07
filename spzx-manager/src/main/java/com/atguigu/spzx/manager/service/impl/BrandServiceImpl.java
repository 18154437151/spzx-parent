package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);  // limit ?,?
        List<Brand> list = brandMapper.findByPage();  // select xxx
        return new PageInfo<>(list);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }
}
