package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 * /system/basic/**
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;
    //查询所有部门

    @ApiOperation(value = "查询所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    //添加部门

    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department){
         return departmentService.addDepartment(department);
    }
    //删除部门
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDepartment(@PathVariable Integer id){
        Department department=new Department();
        department.setId(id);
        return departmentService.deleteDepartment(department);
    }
}
