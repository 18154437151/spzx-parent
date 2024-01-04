package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exp.SpzxGuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        // 带条件的分页查询
        // 1.设置分页参数
        PageHelper.startPage(pageNum,pageSize);  // limit ?,?
        // 2.调用mapper方法，返回数据集合list
        List<SysRole> sysRoleList = sysRoleMapper.selectList(sysRoleDto.getRoleName());  // select * from sys_role where role_name like ? and is_deleted = 0
        // 3.将list封装成pageInfo对象
        PageInfo pageInfo = new PageInfo(sysRoleList);

        return pageInfo;
    }

    @Override
    public void updateRole(SysRole sysRole) {
        // 1.角色名称和角色编号不能为空
        // 非空校验，不允许前端提交空值，角色名称，角色编号，角色id
        Long id = sysRole.getId();
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (id == null){
            throw new SpzxGuiguException(201,"角色id不能为空");
        }
        if (StringUtils.isEmpty(roleName)){
            throw new SpzxGuiguException(201,"角色名称为空");
        }
        if (StringUtils.isEmpty(roleCode)){
            throw new SpzxGuiguException(202,"角色编号为空");
        }
        // 2.根据roleId从mysql中查询当前role对象，
        // 这一步的目的是什么？修改页面提交数据到后端，
        // 判断数据库中该角色的名称和编号和提交过来的名称和编号是否一致，如果不一致，就需要对提交到后端的名称和编号去数据库中检查是否重复
        SysRole sysRoleFromDB = sysRoleMapper.selectById(id);
        // 3.判断角色名称 + 角色编号是否重复
        // if成立：前端提交过来的角色名称和数据库中的角色名称不一致，就需要对提交过来的角色名称去mysql中校验是否存在
        if (!sysRoleFromDB.getRoleName().equals(roleName)){
            Integer countByRoleName = sysRoleMapper.selectCountByRoleName(roleName);
            if (countByRoleName > 0){
                throw new SpzxGuiguException(203,"角色名称已存在");
            }
        }
       // if成立：前端提交过来的角色编号和数据库中的角色编号不一致，就需要对提交过来的角色编号去mysql中校验是否存在
        if (!sysRoleFromDB.getRoleCode().equals(roleCode)){
            Integer countByRoleCode = sysRoleMapper.selectCountByRoleCode(roleCode);
            if (countByRoleCode > 0){
                throw new SpzxGuiguException(204,"角色编号已存在");
            }
        }
        // 3.修改角色
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public void addRole(SysRole sysRole) {
        // 角色名称和角色编号不能为空
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (StringUtils.isEmpty(roleName)){
            throw new SpzxGuiguException(201,"角色名称为空");
        }
        if (StringUtils.isEmpty(roleCode)){
            throw new SpzxGuiguException(202,"角色编号为空");
        }
        // 校验角色名称和角色编号是否重复
        Integer countByRoleName = sysRoleMapper.selectCountByRoleName(roleName);
        if (countByRoleName > 0){
            throw new SpzxGuiguException(203,"角色名称已存在");
        }
        Integer countByRoleCode = sysRoleMapper.selectCountByRoleCode(roleCode);
        if (countByRoleCode > 0){
            throw new SpzxGuiguException(204,"角色编号已存在");
        }
        // 添加新角色
        sysRoleMapper.addRole(sysRole);
    }
}
