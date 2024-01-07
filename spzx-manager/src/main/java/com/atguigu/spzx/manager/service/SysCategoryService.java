package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SysCategoryService {
    List<Category> findByParentId(Long parentId);

    void download(HttpServletResponse httpServletResponse);

    void importExcel(MultipartFile file);
}
