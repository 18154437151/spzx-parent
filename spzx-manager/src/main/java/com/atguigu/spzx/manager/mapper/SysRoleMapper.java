package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> selectList(String roleName);

    Integer selectCountByRoleName(String roleName);

    Integer selectCountByRoleCode(String roleCode);

    void addRole(SysRole sysRole);

    void updateById(SysRole sysRole);

    SysRole selectById(Long id);

    void deleteById(Long roleId);

    List<SysRole> findRoleList();

    List<Long> getRoleIdListByUserId(Long userId);

    void deleteByUserId(Long userId);

    // 逐行关系数据的建立
    void doAssign(@Param("roleId") Long roleId, @Param("userId") Long userId);

    // 批量关系数据的建立
    void batchDoAssign(AssginRoleDto assginRoleDto);
}
