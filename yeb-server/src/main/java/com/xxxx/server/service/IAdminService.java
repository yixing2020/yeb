package com.xxxx.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */

public interface IAdminService extends IService<Admin> {

    /**
     * 登入后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * 通过用户名(userName) 获取 admin 对象
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);
}
