package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import org.apache.ibatis.parsing.TokenHandler;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-05-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {



  /*  @Qualifier(value = "adminMapper")*/
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;//加密

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * 登入后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        //通过页面提交过来的用户名 获取到UserDetails 对象
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //判断用户名或密码错误
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            RespBean.error("用户名或者密码错误!");
        }
        //判断用户是否被禁用
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用,请联系管理员!");
        }

        //更新Security 登入用户对象
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //返回token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHeader",tokenHeader);

        return RespBean.success("登入成功!",tokenMap);
    }

    /**
     * 通过用户名获取admin 对象
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));

    }

    /**
     * 根据用户ID 查询对应的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoleByAdminId(Integer adminId) {
        return adminMapper.getRoleByAdminId(adminId);

    }

    /**
     * 查询所有操作员
     * @param keyword
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keyword) {
        Admin admin=(Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminMapper.getAllAdmins(admin.getId(),keyword);


    }


}
