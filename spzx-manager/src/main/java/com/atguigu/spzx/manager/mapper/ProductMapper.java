package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findList(ProductDto productDto);

    Product getById(Long productId);

    void deleteById(Long productId);

    void updateAuditStatus(@Param("productId") Long productId, @Param("auditStatus") Integer auditStatus,@Param("auditMessage") String auditMessage);

    void updateStatus(@Param("productId") Long productId, @Param("status") Integer status);
}
