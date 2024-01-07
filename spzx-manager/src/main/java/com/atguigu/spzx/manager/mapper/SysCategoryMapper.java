package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCategoryMapper {
    List<Category> findByParentId(Long parentId);

    Integer getCountByParentId(Long id);

    List<Category> findAll();

    void addCategory(Category category);

    void batchAddCategory(List<Category> batchList);

    Map<String,Long> getIdsByThreeId(Long threeId);
}
