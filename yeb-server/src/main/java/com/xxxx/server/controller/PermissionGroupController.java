package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import com.xxxx.server.service.IRoleService;
import com.xxxx.server.service.MenuRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 */
@RestController
@RequestMapping("/system/cfg/permission")
public class PermissionGroupController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private MenuRoleService menuRoleService;


    //获取所有角色

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRole(){
        return roleService.list();
    }

    //添加角色
    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        //如果添加的角色名称不是规定的
        if(!role.getName().startsWith("ROLE_")){
            //从新设置
            role.setName("ROLE_"+role.getName());
        }
        //保存角色
        if(roleService.save(role)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败");

    }
    //删除角色
    @ApiOperation(value = "根据ID删除角色")
    @DeleteMapping("role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    //批量删除角色

    @ApiOperation(value = "根据ID批量删除角色")
    @DeleteMapping("/")
    public RespBean deleteRoleByIds(Integer [] ids){
        if(roleService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    /**
     * 查询所有菜单
     */

    @ApiOperation(value = "查询所有菜单")
    @PostMapping("/menus")
    public List<Menu> getAllMeun(){
        return menuService.getAllMeun();
    }

    /**
     * 根据角色ID查询菜单ID
     */
    @ApiOperation(value ="根据角色ID查询菜单ID")
    @GetMapping("/Mid/{rid}")
    //.stream().map(MenuRole::getMid).collect(Collectors.toList()); 将查询出来的对象转换成一个list
    public List<Integer> getMenuIdByRoleId(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateRoleMenu(@PathVariable Integer rid,Integer[] mids){
        return menuRoleService.updateRoleMenu(rid,mids);

    }



}
