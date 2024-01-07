package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> selectList(CategoryBrandDto categoryBrandDto);

    Integer selectCount(CategoryBrandDto categoryBrandDto);

    void addCategoryBrand(CategoryBrandDto categoryBrandDto);

    void deleteById(Long id);

    CategoryBrand getById(Long id);

    void updateById(CategoryBrand categoryBrand);
}
