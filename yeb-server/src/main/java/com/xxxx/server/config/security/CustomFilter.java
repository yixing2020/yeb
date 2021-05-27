package com.xxxx.server.config.security;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * 权限控制
 * 根据请求url判断权限的过滤器
 */
@Component
public class CustomFilter  implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IMenuService menuService;
    AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //通过调用自定义的方法获取到menus 对象,对象里面有role 对象的集合
        List<Menu> menus = menuService.getMenuByRole();

        for(Menu menu:menus){
            //判断请求的url 与角色是否匹配
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                //能匹配上
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                //将角色放入到list中
                SecurityConfig.createList(str);
            }
        }
        //没匹配的url默认登入可以访问
        return  SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
