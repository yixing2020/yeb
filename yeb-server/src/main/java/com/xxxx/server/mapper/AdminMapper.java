package com.xxxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
@Service
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 根据用户ID 查询对应的角色
     * @param adminId
     * @return
     */
    List<Role> getRoleByAdminId(Integer adminId);

    /**
     * 查询所有操作员
     * @param id
     * @param keyword
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keyword") String keyword);
}
