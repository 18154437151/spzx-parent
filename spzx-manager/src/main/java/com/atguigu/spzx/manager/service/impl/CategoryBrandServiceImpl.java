package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exp.SpzxGuiguException;
import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private SysCategoryMapper sysCategoryMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<CategoryBrand> list = categoryBrandMapper.selectList(categoryBrandDto);
        return new PageInfo(list);
    }

    @Override
    public void addCategoryBrand(CategoryBrandDto categoryBrandDto) {
        // 1.检查该分类+该品牌关联数据是否存在，如果存在，直接抛出异常
        Integer count = categoryBrandMapper.selectCount(categoryBrandDto);
        if(count>0){
            throw new SpzxGuiguException(201,"该品牌和该分类的关系数据已存在，请勿重复创建！");
        }
        // 2.建立关系数据
        categoryBrandMapper.addCategoryBrand(categoryBrandDto);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Long> getIdList(Long threeId) {
        Map<String,Long> idsByThreeId = sysCategoryMapper.getIdsByThreeId(threeId);
        List<Long> idList = Arrays.asList(idsByThreeId.get("one"), idsByThreeId.get("two"), idsByThreeId.get("three"));
        return idList;
    }

    @Override
    public void updateCategoryBrand(CategoryBrand categoryBrand) {
        // 1.非空校验
        Long id = categoryBrand.getId();
        Long brandId = categoryBrand.getBrandId();
        Long categoryId = categoryBrand.getCategoryId();
        // 2.根据id查询关系数据
        CategoryBrand categoryBrandFromDB = categoryBrandMapper.getById(id);
        if (categoryBrandFromDB.getBrandId()==brandId && categoryBrandFromDB.getCategoryId()==categoryId){
            throw new SpzxGuiguException(201,"信息未修改");
        }
        // 3.判断关系数据是否重复（根据传递过来的brandId和categoryId去查询是否已经存在关系记录）
        CategoryBrandDto categoryBrandDto = new CategoryBrandDto();
        categoryBrandDto.setBrandId(brandId);
        categoryBrandDto.setCategoryId(categoryId);
        Integer integer = categoryBrandMapper.selectCount(categoryBrandDto);
        if (integer > 0){
            throw new SpzxGuiguException(201,"该关系数据已存在，请勿重复建立");
        }
        // 4.执行update更新语句
        categoryBrandMapper.updateById(categoryBrand);
    }
}
