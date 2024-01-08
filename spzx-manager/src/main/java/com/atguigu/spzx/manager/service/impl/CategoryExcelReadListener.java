package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryExcelReadListener extends AnalysisEventListener<CategoryExcelVo> {
    // 该集合的最大容量，存储10个数据，每读取10行数据，导入到数据库一次，减少执行的sql语句
    private List<Category> batchList = ListUtils.newArrayListWithExpectedSize(10);
    @Autowired
    private SysCategoryMapper sysCategoryMapper;
    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext context) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryExcelVo,category);
        batchList.add(category);
        if (batchList.size() == 10){
            // 执行一次insert
            sysCategoryMapper.batchAddCategory(batchList);
            batchList = ListUtils.newArrayListWithExpectedSize(10);  // 原来的集合会自动gc回收，释放空间
        }
//        System.out.println(categoryExcelVo);
//        sysCategoryMapper.addCategory(category);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        System.out.println("所有行读取完毕");
        if (batchList.size() > 0){
            sysCategoryMapper.batchAddCategory(batchList);
            batchList = ListUtils.newArrayListWithExpectedSize(10);  // 原来的集合会自动gc回收，释放空间
        }
    }
}
