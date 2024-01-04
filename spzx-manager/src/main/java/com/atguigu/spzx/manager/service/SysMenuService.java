package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findByParentId(long parentId);

    void addMenu(SysMenu sysMenu);

    void deleteMenu(Long menuId);

    void assignMenuForRole(AssginMenuDto assginMenuDto);

    List<Long> findMenuIdListByRoleId(Long roleId);
}
