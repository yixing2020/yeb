package com.xxxx.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制器
 */
@Controller
public class HellController {

    @GetMapping("/employee/basic/hello")
    public String hello(){
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello2(){
        return "/employee/advanced/hello";
    }


}
