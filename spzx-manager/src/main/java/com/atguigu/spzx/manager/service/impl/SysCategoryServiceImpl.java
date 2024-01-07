package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.manager.service.SysCategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SysCategoryServiceImpl implements SysCategoryService {
    @Autowired
    private SysCategoryMapper sysCategoryMapper;
    @Autowired
    private CategoryExcelReadListener categoryExcelReadListener;
    @Override
    public List<Category> findByParentId(Long parentId) {
        // 先根据parentId查询分类列表
        List<Category> list = sysCategoryMapper.findByParentId(parentId);
        // 判断每个分类是否存在下级
        list.forEach(category -> {
            Integer count = sysCategoryMapper.getCountByParentId(category.getId());
            category.setHasChildren(count>0);
        });
        return list;
    }

    @Override
    public void download(HttpServletResponse httpServletResponse) {
        try {
            // 1.设置响应头
            // Content-Type: 指定客户端下载的文件的类型
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 响应编码
            httpServletResponse.setCharacterEncoding("utf-8");
            // Content-disposition: 用于指定下载的文件的名称，该名称需要使用URLEncoder重新编码，防止文件名的中文乱码
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("全部商品分类", "utf-8");
            httpServletResponse.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 从category表中查询到要被导出的数据
            List<Category> categoryList = sysCategoryMapper.findAll();
            // 将上一步的分类集合进行泛型的转换
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            categoryList.forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
//                categoryExcelVo.setId(category.getId());
//                categoryExcelVo.setParentId(category.getParentId());
//                categoryExcelVo.setOrderNum(category.getOrderNum());
//                categoryExcelVo.setName(category.getName());
//                categoryExcelVo.setStatus(category.getStatus());
//                categoryExcelVo.setImageUrl(category.getImageUrl());
                // spring中提供的工具类，用于将category对象中的属性拷贝到excelVo对象中
                // 相同名称的属性，才可以完成拷贝  a --> a
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);

            });
            // 调用EasyExcel的核心api,三个方法
            EasyExcel.write(httpServletResponse.getOutputStream(), CategoryExcelVo.class).sheet("全部商品分类").doWrite(categoryExcelVoList);
        }catch (IOException e){
            throw new RuntimeException(e);
    }
    }

    @Override
    public void importExcel(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, CategoryExcelVo.class,categoryExcelReadListener)
                    // 切记：你的excel的sheet表名称
                    .sheet("Sheet1")  // 指定你要读取哪一个sheet表
                    .doRead();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
