package com.xxxx.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("com.xxxx.server.mapper")
public class VoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoaApplication.class);

    }
}
