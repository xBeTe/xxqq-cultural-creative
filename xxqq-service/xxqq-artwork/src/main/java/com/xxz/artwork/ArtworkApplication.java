package com.xxz.artwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xzxie
 * @create 2023/12/16 21:27
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xxz.artwork.mapper")
public class ArtworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtworkApplication.class, args);
    }
}
