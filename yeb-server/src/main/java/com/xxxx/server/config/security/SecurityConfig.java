package com.xxxx.server.config.security;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Sercurity 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private RestAutorizationEntryPtion restAutorizationEntryPtion;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManger customUrlDecisionManger;


    /**
     * 自定义放行静态资源,不走security 拦截器链
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login","/logout","/css/**","/js/**","/index.html","favicon.ico","/doc.html"
                ,"/webjars/**","/swagger-resources/**","/v2/api-docs/**"

        );

    }

    /**
     * 走我们自己重写的userDatails
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置security
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                //基于token 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //url 控制
                .authorizeRequests()
                //放行某些资源,不需要认证
                //.antMatchers("/login","logout")
                //.permitAll()
                //除了上面所有请求都需要认证
                .anyRequest()
                .authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManger);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        //添加jwt登入授权过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权,和未登入结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)//权限不足 时候返回
                .authenticationEntryPoint(restAutorizationEntryPtion);//token 失效时候的返回
    }

    /**
     *
     * @return
     */
    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){

        return  new JwtAuthencationTokenFilter();
    }

    /**
     * 重写userDatailsService
     * 根据用户名验证
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Admin admin = iAdminService.getAdminByUserName(username);
            if(admin!=null){
                admin.setRoles(iAdminService.getRoleByAdminId(admin.getId()));
                return  admin;
            }
            throw new UsernameNotFoundException("登入失败!");
        };
    }

    /**
     * secutry 验证密码是否正确
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
