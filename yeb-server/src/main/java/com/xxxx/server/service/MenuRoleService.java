package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.SysMsg;

public interface MenuRoleService  extends IService<MenuRole> {


    /**
     * 根据角色id更新菜单id
     * @param rid
     * @param mids
     */
    RespBean updateRoleMenu(Integer rid, Integer[] mids);
}
