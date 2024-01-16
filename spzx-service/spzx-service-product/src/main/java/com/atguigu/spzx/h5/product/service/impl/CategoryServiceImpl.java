package com.atguigu.spzx.h5.product.service.impl;

import com.atguigu.spzx.h5.product.mapper.IndexMapper;
import com.atguigu.spzx.h5.product.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private IndexMapper indexMapper;
    @Override
    public List<Category> findCategoryTree() {
        // 所有的分类
        List<Category> allCategoryList = indexMapper.findAll();
        // 筛选出所有的一级分类,
        // 如果当前分类的parent_id不等于0，则向集合中存放一个null
        // 对list集合进行遍历，每次拿到一个Category对象，如果当前Category对象的parentId==0,就保留该category对象到list集合中；如果不等于0，将null保留到list集合中
//        List<Category> collect = allCategoryList.stream().map(category -> {
//            if (category.getParentId() == 0){
//                return category;
//            }else {
//                return null;
//            }
//        }).collect(Collectors.toList());
        // 对list集合进行遍历，每次拿到一个Category对象，如果当前Category对象的parentId==0,就保留该category对象到list集合中
        List<Category> oneList = allCategoryList.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());
        oneList.forEach(one -> {
            Long id = one.getId();
            // 该一级分类下的二级分类
            List<Category> twoList = allCategoryList.stream().filter(category -> category.getParentId() == id).collect(Collectors.toList());
            twoList.forEach(two->{
                Long twoId = two.getId();
                List<Category> threeList = allCategoryList.stream().filter(category -> category.getParentId() == twoId).collect(Collectors.toList());
                two.setChildren(threeList);
            });
            one.setChildren(twoList);
        });
        return oneList;
    }
//    @Override
//    public List<Category> findCategoryTree() {
//        List<Category> oneList = indexMapper.findByParentId(0L);
//        oneList.forEach(category -> {
//            List<Category> two = indexMapper.findByParentId(category.getId());
//            two.forEach(twoCategory -> {
//                List<Category> threeList = indexMapper.findByParentId(twoCategory.getId());
//                twoCategory.setChildren(threeList);
//            });
//            category.setChildren(two);
//        });
//        return oneList;
//    }
}
