package com.xxxx.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    //荷载里面的key 用户名
    private static final String CLAIN_KEY_USERNAME="sub";
    //jwt 创建时间就
    private static final String CLAIN_KEY_CREATED="created";
    //jwt 密钥
    @Value("${jwt.secret}")
    private String sercret;
    //jwt 失效时间
    @Value("${jwt.expiration}")
    private long expiration;


    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        //创建荷载
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIN_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIN_KEY_CREATED,new Date());

        return  generateToken(claims);


    }

    /**
     * 设置生成token的方法
     * @param claims
     * @return
     */
    public  String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)//设置荷载
                .setExpiration(generateExpirationDate())//设置失效时间,generateExpirationDate()是创建失效时间
                .signWith(SignatureAlgorithm.HS512,sercret)//设置签名
                .compact();

    }

    /**
     * 根据token 获得用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username;
        //从token中获取荷载
        try{
            Claims claims= getTokenFromClaims(token);
            //通过荷载获取用户名
            username = claims.getSubject();
        }catch (Exception e){
            username=null;
        }
        return username;

    }


    /**
     * 判断token 是否可以刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return  !isTokenExpiration(token);
    }

    /**
     * 刷新token 方法
     * @param token
     * @return
     */
    public String refreshToken(String token){
        //获取荷载
        Claims claims = getTokenFromClaims(token);
        //设置新的时间
        claims.put(CLAIN_KEY_CREATED,new Date());
        //创建token
        return  generateToken(claims);
    }

    /**
     * 验证token 是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validatetoken(String token, UserDetails userDetails){
        //获取token 中的用户名
        String username = getUserNameFromToken(token);
        //判断是否一致
        return  username.equals(userDetails.getUsername())&&!isTokenExpiration(token);
    }

    /**
     * 判断token 是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpiration(String token) {
        //获取失效时间
        Date expiration =getDateExpirationFromToken(token);
        return  expiration.before(new Date());
    }

    /**
     * 获取失效时间
     * @param token
     * @return
     */
    private Date getDateExpirationFromToken(String token) {
        //获取荷载
        Claims claims = getTokenFromClaims(token);
        Date expiration = claims.getExpiration();
        return expiration;
    }

    /**
     * 通过token 获得荷载
     * @param token
     * @return
     */
    private Claims getTokenFromClaims(String token) {
        Claims claims=null;
        //获取荷载

        try {
            claims = Jwts.parser()
                    .setSigningKey(sercret)//密钥
                    .parseClaimsJws(token)//token
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;

    }



    /**
     * token 的失效时间转换
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
