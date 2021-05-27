package com.xxxx.server.service.impl;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 查询所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartment() {

        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
         departmentMapper.addDepartment(department);
         if(1==department.getResult()){
             //添加成功
             return RespBean.success("添加成功",department);

         }
         return RespBean.error("添加失败!");
    }

    /**
     * 删除部门
     * @param department
     * @return
     */
    @Override
    public RespBean deleteDepartment(Department department) {

        departmentMapper.deleteDepartment(department);
        if(-2==department.getResult()){
            return RespBean.success("该部门下还有子部门,删除失败!");
        }
        if(-1==department.getResult()){
            return RespBean.success("该部门下还有员工,删除失败!");
        }
        if(1==department.getResult()){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败!");

    }
}
