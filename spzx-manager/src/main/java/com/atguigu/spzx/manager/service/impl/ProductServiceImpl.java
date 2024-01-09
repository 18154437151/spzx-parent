package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exp.SpzxGuiguException;
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

    @Override
    public void updateAuditStatus(Long productId, Integer auditStatus) {
        // 1.取值范围的校验
        if (auditStatus != 1 && auditStatus != -1){
            throw new SpzxGuiguException(201,"审核状态取值范围不正确");
        }
        // 2.执行sql,update product set auditStatus = #{auditStatus} where id = #{productId} and is_deleted = 0
        String auditMessage = auditStatus == 1?"审核通过":"审核未通过";
        productMapper.updateAuditStatus(productId,auditStatus,auditMessage);
    }

    @Override
    public void updateStatus(Long productId, Integer status) {
        // 1.对于status的取值范围进行校验 [-1,1]
        if (status != 1 && status != -1){
            throw new SpzxGuiguException(201,"上下架状态的取值范围不正确");
        }
        // 2.如果当前spu未审核通过，则不允许上架,如果未审核通过，抛出异常，给出提示
        // 根据productId查询Product对象，判断auditStatus是否等于1
        Product product = productMapper.getById(productId);
        if (product == null){
            throw new SpzxGuiguException(201,"该商品不存在");
        }
        Integer auditStatus = product.getAuditStatus();
        if (auditStatus != 1){
            throw new SpzxGuiguException(201,"该商品未审核通过，不允许上下架");
        }
        // 3.判断是否重复操作，抛出异常，给出提示，如果已上架，再进行上架，就是重复操作
        if (product.getStatus() == status){
            throw new SpzxGuiguException(201,"该商品已" + (status == 1?"上架":"下架") + "，请勿重复操作");
        }
        // 4.spu审核通过，修改spu和多个sku的status 全部进行修改
        productMapper.updateStatus(productId,status);
        productSkuMapper.updateStatus(productId,status);
    }
}