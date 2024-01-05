package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findByParentId(long parentId);

    void addMenu(SysMenu sysMenu);

    void deleteById(Long menuId);

    void deleteByRoleId(Long roleId);

    void doAssignRoleMenu(AssginMenuDto assginMenuDto);

    List<Long> findMenuIdListByRoleId(Long roleId);

    List<SysMenu> menusByUserIdAndParentId(@Param("userId") Long userId,@Param("parentId") Long parentId);
}
