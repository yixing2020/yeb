package com.xxxx.server.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制
 * 判断用户角色
 */
@Component
public class CustomUrlDecisionManger implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            //当前url所需要的一个角色
            String attribute = configAttribute.getAttribute();
            //判断这个角色是否登入就可以访问的角色,ROLE_LOGIN 是在CustomFilter中设置的
            if("ROLE_LOGIN".equals(attribute)){
                //判断是否登入
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("还未登入!请登入");
                }else{
                    return;
                }

            }
            //判断用户角色是否为url所需要的角色
            //获取到权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(attribute)){
                    return;
                }
            }


        }
        throw new AccessDeniedException("权限不足!请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
