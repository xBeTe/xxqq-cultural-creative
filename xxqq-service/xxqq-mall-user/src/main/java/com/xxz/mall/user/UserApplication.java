package com.xxz.mall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xzxie
 * @create 2023/11/7 16:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xxz.mall.user.mapper")
@EnableFeignClients("com.xxz.apis")
public class UserApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);
    }
}
