package com.xxz.mall.gateway.config;

import com.xxz.model.config.JwtProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xzxie
 * @create 2023/11/7 10:39
 */
@Configuration
@Slf4j
public class JwtConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperty jwtProperty() {
        return new JwtProperty();
    }
}
