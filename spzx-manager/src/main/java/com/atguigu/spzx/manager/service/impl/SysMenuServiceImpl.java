package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exp.SpzxGuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> findByParentId(long parentId) {
        List<SysMenu> list = sysMenuMapper.findByParentId(parentId);
        list.forEach(sysMenu -> {
            sysMenu.setChildren(this.findByParentId(sysMenu.getId()));
        });
        return list;
    }

    @Override
    public void addMenu(SysMenu sysMenu) {
        // 非空校验
        sysMenuMapper.addMenu(sysMenu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        // 1.判断当前菜单是否存在下级菜单,如果存在下级菜单，抛出异常
        List<SysMenu> children = sysMenuMapper.findByParentId(menuId);
        if (children != null && children.size()>0){
            throw new SpzxGuiguException(201,"当前菜单存在下级，不允许删除！");
        }
        // 2.逻辑删除
        sysMenuMapper.deleteById(menuId);
    }
}
