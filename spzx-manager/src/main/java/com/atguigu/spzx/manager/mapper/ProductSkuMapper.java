package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    List<ProductSku> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    void updateStatus(@Param("productId") Long productId,@Param("status") Integer status);

    void add(ProductSku productSku);
}
