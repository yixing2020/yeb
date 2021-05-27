package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户ID查询菜单
     * @return
     */
    List<Menu> getMenusByAdminId();

    /**
     * 根据角色查询菜单列表
     *
     */
    List<Menu> getMenuByRole();

    /**
     * 查询所有菜单
     */
    List<Menu> getAllMeun();
}
