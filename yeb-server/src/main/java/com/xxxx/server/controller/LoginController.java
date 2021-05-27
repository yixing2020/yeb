package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 *登入控制器
 */

@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService iAdminService;
    @ApiOperation(value = "登入后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return iAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),request);
    }

    @ApiOperation(value = "获取当前登入用户的信息")
    @PostMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(principal==null){
            return  null;
        }
        String username = principal.getName();
        Admin admin= iAdminService.getAdminByUserName(username);
        admin.setPassword(null);
        //将获取到的角色列表设置到admin对象中去
        admin.setRoles(iAdminService.getRoleByAdminId(admin.getId()));
        return admin;
    }
    @ApiOperation(value="退出登入")
    @GetMapping("/logout")
    public RespBean logout(){
        return RespBean.success("退出成功");
    }

}
