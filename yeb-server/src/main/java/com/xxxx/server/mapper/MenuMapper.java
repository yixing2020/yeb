package com.xxxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID 查询菜单
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色查询菜单列表
     * @return
     */
    List<Menu> getMenuByRole();

    /**
     * 查询所有子菜单
     * @return
     */
    List<Menu> getAllMeun();
}
