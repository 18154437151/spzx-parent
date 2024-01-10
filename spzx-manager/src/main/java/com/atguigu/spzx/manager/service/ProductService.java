package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo findByPage(Integer pageNum, Integer pageSize, ProductDto productDto);

    Product getByProductId(Long productId);

    void deleteById(Long productId);

    void updateAuditStatus(Long productId, Integer auditStatus);

    void updateStatus(Long productId, Integer status);

    void add(Product product);
}
