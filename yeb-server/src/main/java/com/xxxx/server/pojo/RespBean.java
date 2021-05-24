package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 公共返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class RespBean {

    private  Integer code;//返回的状态码
    private  String msg; //返回的消息
    private  Object obj; //返回的对象


    /**
     * 成功返回
     * @param msg
     * @return
     */
    public static RespBean success(String msg){
        return  new RespBean(200,msg,null);
    }

    /**
     * 成功返回
     * @param msg
     * @param obj
     * @return
     */
    public  static RespBean success(String msg,Object obj){
        return  new RespBean(200,msg,obj);
    }

    /**
     * 失败返回
     * @param msg
     * @return
     */
    public static RespBean  error(String msg){
        return  new RespBean(500,msg,null);
    }

    /**
     * 失败返回
     * @param msg
     * @return
     */
    public static RespBean  error(String msg,Object obj){
        return  new RespBean(500,msg,obj);
    }
}
