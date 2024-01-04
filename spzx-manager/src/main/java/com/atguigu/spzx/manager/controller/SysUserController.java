package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/system/sysUser")
@RestController
@Tag(name = "用户管理")
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "根据指定的id，删除系统用户")
    @DeleteMapping("/deleteSysUser/{id}")
    public Result deleteSysUser(@PathVariable Long id){
        sysUserService.deleteSysUser(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "批量删除")
    @DeleteMapping("/deleteBatch")  // 在请求体中传递id数组
    public Result deleteSysUser(@RequestBody List<Long> ids){
        sysUserService.deleteBatch(ids);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "添加系统用户")
    @PostMapping("/addSysUser")
    public Result addSysUser(@RequestBody SysUser sysUser){
        sysUserService.addSysUser(sysUser);
        // 不需要返回业务数据（data为null即可）
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }
    // 注意：后端：GetMapping + 不要添加 @RequestBody ,前端：params属性
    // 后端：PostMapping + 可以添加 @RequestBody ,前端：data属性
    @Operation(summary = "查询用户列表")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysUserDto sysUserDto){
        PageInfo pageInfo = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
//        List list = pageInfo.getList();
//        long total = pageInfo.getTotal();
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "修改系统用户")
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
