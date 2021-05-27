package com.xxxx.server.controller;


import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
@RestController
//因为我们数据库中已经定义了映射路径,所以需要根据数据库定义的路径进行映射
@RequestMapping("/system/cfg")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "根据用户id查询菜单")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        //因为登入了,所以我们一般是从服务器端获取,不需要从前端传递过来
        List<Menu> menuList= menuService.getMenusByAdminId();
        return menuList;
    }




}
