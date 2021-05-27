package com.xxxx.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 根据用户id 查询对应的角色
     * @param adminId
     * @return
     */
    List<Role> getRoleByAdminId(Integer adminId);


    /**
     * 查询所有操作员
     * @param keyword
     * @return
     */
    List<Admin> getAllAdmins(String keyword);
}
