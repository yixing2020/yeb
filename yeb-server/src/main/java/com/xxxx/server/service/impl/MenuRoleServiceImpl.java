package com.xxxx.server.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuRoleMapper;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.MenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    /**
     * 根据角色id更新菜单id
     * @param rid
     * @param mids
     */
    @Override
    public RespBean updateRoleMenu(Integer rid, Integer[] mids) {
        //根据角色id删除菜单id
        int isdel = menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if(null==mids||0==mids.length){
            return RespBean.success("更新成功!");
        }
        //批量更新
        Integer integer = menuRoleMapper.insertRecord(rid, mids);
        if(integer==mids.length){
            return RespBean.success("跟新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
