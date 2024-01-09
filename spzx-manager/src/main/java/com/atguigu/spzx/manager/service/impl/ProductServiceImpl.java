package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, ProductDto productDto) {
        PageHelper.startPage(pageNum,pageSize);
        // 1.查询spu列表，关联查询4个名称
        List<Product> list = productMapper.findList(productDto);
        // 如果前端表格只是显示spu的基本信息+品牌名称+3个分类名称，就不需要这里的遍历
        list.forEach(product -> {
            // spu商品的id
            Long productId = product.getId();
            System.out.println("productId的内容为：------------------");
            System.out.println(productId);
            // 2.为每个spu，查询sku列表
            product.setProductSkuList(productSkuMapper.findByProductId(productId));
            // 3.为每个spu,查询详情图列表
            ProductDetails productDetails = productDetailsMapper.getByProductId(productId);
            if (productDetails != null){
                product.setDetailsImageUrls(productDetails.getImageUrls());  // u1.u2.u3...
            }
        });
        return new PageInfo(list);
    }

    @Override
    public Product getByProductId(Long productId) {
        Product product = productMapper.getById(productId);  // 基本信息+4个名称
        product.setProductSkuList(productSkuMapper.findByProductId(productId));
        product.setDetailsImageUrls(productDetailsMapper.getByProductId(productId).getImageUrls());
        return null;
    }

    @Override
    public void deleteById(Long productId) {
        productMapper.deleteById(productId);
        productSkuMapper.deleteByProductId(productId);
        productDetailsMapper.deleteByProductId(productId);
    }
}