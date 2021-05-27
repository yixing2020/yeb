package com.xxxx.server.config.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//jwt 登入过滤器
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 前置拦击
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain 拦截方法
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String autheader = httpServletRequest.getHeader(this.tokenHeader);
        //判断用户是否存在token
        if(null!=autheader&& autheader.startsWith(this.tokenHead)){
            String authToken = autheader.substring(this.tokenHead.length());
            //根据token 获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            //token 存在用户名,但未登入 通过 SecurityContextHolder.getContext().getAuthentication() 上下文全局对象获取对象
            if(null!=username&&null== SecurityContextHolder.getContext().getAuthentication()){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效,如果有效从新设置用户对象
                if(jwtTokenUtil.validatetoken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //从新设置
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
