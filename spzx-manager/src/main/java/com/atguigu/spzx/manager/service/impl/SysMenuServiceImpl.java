package com.atguigu.spzx.manager.service.impl;


import com.atguigu.spzx.common.exp.SpzxGuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl  implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

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
        //非空校验
        sysMenuMapper.addMenu(sysMenu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        //1、判断当前菜单是否存在下级，如果存在下级，抛出异常
        List<SysMenu> children = sysMenuMapper.findByParentId(menuId);
        if(children!=null && children.size()>0){
            throw new SpzxGuiguException(201,"当前菜单存在下级，不允许删除！");
        }
        //2、逻辑删除
        sysMenuMapper.deleteById(menuId);
    }

    @Override
    public void assignMenuForRole(AssginMenuDto assginMenuDto) {
        //1、从sys_role_menu 角色和菜单的关系表中，根据roleId删除关系数据（物理删除）
        sysMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        //2、重新建立该角色和多个菜单的对应关系
        sysMenuMapper.doAssignRoleMenu(assginMenuDto);
    }

    @Override
    public List<Long> findMenuIdListByRoleId(Long roleId) {
        return sysMenuMapper.findMenuIdListByRoleId(roleId);
    }

    @Override
    public List<SysMenuVo> menus(Long userId, Long parentId) {

        //1、调用私有方法，返回一级菜单列表，此时的泛型SysMenu
        List<SysMenu> sysMenuList = this.getSysMenuList(userId, parentId);

        //2、将List<SysMenu> 泛型转成  List<SysMenuVo>
        List<SysMenuVo> sysMenuVoList = this.sysMenuToVo(sysMenuList);

        return sysMenuVoList;
    }

    private List<SysMenuVo> sysMenuToVo(List<SysMenu> sysMenuList) {

        List<SysMenuVo> sysMenuVoList = new ArrayList<>();


        sysMenuList.forEach(sysMenu -> {

            //每个sysMenu转成sysMenuVo
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());

            if(sysMenu.getChildren()!=null && sysMenu.getChildren().size()>0){

                List<SysMenu> children = sysMenu.getChildren();
                List<SysMenuVo> childrenVoList = this.sysMenuToVo(children);

                sysMenuVo.setChildren(childrenVoList);
            }else{
                sysMenuVo.setChildren(null);
            }

            sysMenuVoList.add(sysMenuVo);

        });

        return sysMenuVoList;
    }

    /**
     * 第一个私有方法：返回一级菜单列表，泛型SysMenu，每个菜单的下级一同查询到
     * @param userId
     * @param parentId
     * @return
     */
    private List<SysMenu> getSysMenuList(Long userId, Long parentId){
        List<SysMenu> list = sysMenuMapper.menusByUserIdAndParentId(userId,parentId);
        list.forEach(sysMenu -> {
            sysMenu.setChildren(this.getSysMenuList(userId,sysMenu.getId()));
        });
        return list;
    }


}
