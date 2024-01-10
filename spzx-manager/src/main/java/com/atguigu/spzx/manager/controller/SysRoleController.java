package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.Log;
import com.atguigu.spzx.common.log.OperatorType;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "查询角色列表")
    @GetMapping("/findRoleList")
    public Result finRoleList(){
        List<SysRole> list = sysRoleService.findRoleList();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询当前用户已分配的角色id集合")
    @GetMapping("/getRoleIdListByUserId/{userId}")
    public Result getRoleIdListByUserId(@PathVariable Long userId){
        List<Long> roleIdList = sysRoleService.getRoleIdListByUserId(userId);
        return Result.build(roleIdList,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除角色")
    @DeleteMapping("/deleteRole/{roleId}")
    public Result deleteRole(@PathVariable Long roleId){
        sysRoleService.deleteRole(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findRoleListAndRoleIdList/{userId}")
    public Result findRoleListAndRoleIdList(@PathVariable Long userId){
        List<Long> roleIdList = sysRoleService.getRoleIdListByUserId(userId);
        List<SysRole> roleList = sysRoleService.findRoleList();
        System.out.println("---------------------------------------");
        System.out.println("roleIdList的内容为；" + roleIdList);
        System.out.println("roleList的内容为：" + roleList);
        System.out.println("--------------------------------------------------");
        HashMap hashMap = new HashMap();
        hashMap.put("roleIdList",roleIdList);
        hashMap.put("roleList",roleList);
        return Result.build(hashMap,ResultCodeEnum.SUCCESS);
    }

    @Log(title = "角色管理：查询角色列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @Operation(summary = "查询角色列表")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,@RequestBody SysRoleDto sysRoleDto){
        PageInfo pageInfo = sysRoleService.findByPage(pageNum,pageSize,sysRoleDto);
        if (pageNum == -1){
            int i = 1/0;
        }
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

    @Operation(summary = "为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysRoleService.doAssign(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

//    @Operation(summary = "为用户分配角色")
//    @PostMapping("/doAssign/{userId}")
//    public Result doAssign(@RequestBody List<Long> roleIds,@PathVariable Long userId){
//
//    }
}
