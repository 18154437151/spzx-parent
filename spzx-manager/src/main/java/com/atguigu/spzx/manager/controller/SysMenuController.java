package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/system/sysMenu")
@RestController
@Tag(name = "菜单管理")
@CrossOrigin
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Operation(summary = "查询一级菜单列表（关联查询下级列表）")
    @GetMapping("/findMenuList")
    public Result findMenuList(){
        // 返回一级菜单列表
        List<SysMenu> list = sysMenuService.findByParentId(0L);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.addMenu(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/deleteMenu/{menuId}")
    public Result deleteMenu(@PathVariable Long menuId){
        sysMenuService.deleteMenu(menuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "为角色分配菜单")
    @PostMapping("/assignMenuForRole")
    public Result assignMenuForRole(@RequestBody AssginMenuDto assginMenuDto){
        sysMenuService.assignMenuForRole(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据角色id查询已分配的菜单id集合")
    @GetMapping("/findMenuIdListByRoleId/{roleId}")
    public Result findMenuIdListByRoleId(@PathVariable Long roleId){
        List<Long> menuIdList = sysMenuService.findMenuIdListByRoleId(roleId);
        return Result.build(menuIdList,ResultCodeEnum.SUCCESS);
    }
}
