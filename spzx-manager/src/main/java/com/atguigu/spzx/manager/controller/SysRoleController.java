package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Operation(summary = "删除角色")
    @DeleteMapping("/deleteRole/{roleId}")
    public Result deleteRole(@PathVariable Long roleId){
        sysRoleService.deleteRole(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询角色列表")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,@RequestBody SysRoleDto sysRoleDto){
        PageInfo pageInfo = sysRoleService.findByPage(pageNum,pageSize,sysRoleDto);
//        List list = pageInfo.getList(); // 当前页的结果集
//        long total = pageInfo.getTotal();  // 总记录数
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加角色（角色名称和角色编号不允许重复）")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        sysRoleService.addRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole){
        sysRoleService.updateRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
