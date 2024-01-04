package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface SysUserMapper {
    List<SysUser> findAll();

    SysUser selectByUsername(String userName);

    List<SysUser> findList(SysUserDto sysUserDto);

    Integer selectByPhone(String phone);

    void addSysUser(SysUser sysUser);

    SysUser selectById(Long id);

    void updateById(SysUser sysUser);

    void deleteById(Long id);
}
