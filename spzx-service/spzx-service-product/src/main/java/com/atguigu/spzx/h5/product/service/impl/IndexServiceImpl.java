package com.atguigu.spzx.h5.product.service.impl;

import com.atguigu.spzx.h5.product.mapper.IndexMapper;
import com.atguigu.spzx.h5.product.service.IndexService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;

    @Override
    public Map getMap() {
        // productSkuList + categoryList
        HashMap hashMap = new HashMap();
        List<ProductSku> productSkuList = indexMapper.getProductSkuList();
        List<Category> categoryList = indexMapper.getCategoryList();
        hashMap.put("productSkuList",productSkuList);  // sku商品列表
        hashMap.put("categoryList",categoryList);  // 一级分类列表
        return hashMap;
    }
}
