package com.xxz.auth;

import com.xxz.common.jwt.JwtProperties;
import com.xxz.common.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xzxie
 * @create 2023/11/13 22:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.xxz.apis")
@Slf4j
public class AuthApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);
    }
}
