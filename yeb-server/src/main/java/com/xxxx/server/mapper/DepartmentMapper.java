package com.xxxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 查询所有部门
     * @return
     */
    List<Department> getAllDepartment(Integer id);



    /**
     * 添加部门
     * @param department
     * @return
     */
    void addDepartment(Department department);

    /**
     * 删除部门
     * @param department
     */
    void deleteDepartment(Department department);
}
