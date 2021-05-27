package com.xxxx.server.service.impl;

/*import com.xxxx.pojo.Menu;
import com.xxxx.mapper.MenuMapper;
import com.xxxx.service.IMenuService;*/
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据用户ID查询菜单
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        //通过securtiy 全局对象获取到当前登入主体,然后获取用户id
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //redis 取值对象
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //首先从redis中获取菜单
        List<Menu> menus = (List<Menu>) valueOperations.get("meun_" + admin.getId());
        if(CollectionUtils.isEmpty(menus)){
            //如果menus 是空的,从数据库中查询,并存入到redis中
            menus= menuMapper.getMenusByAdminId(admin.getId());
            valueOperations.set("meun"+admin.getId(),menus);
        }

        return menus;
    }

    /**
     * 根据角色查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByRole() {
        return menuMapper.getMenuByRole();
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMeun() {

        return menuMapper.getAllMeun();
    }
}
