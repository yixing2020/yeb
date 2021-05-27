package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
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
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @ApiOperation(value="查询所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keyword){
        return  adminService.getAllAdmins(keyword);
    }

}
