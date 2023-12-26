package com.xxz.auth.config;

import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xzxie
 * @create 2023/11/14 20:40
 */
@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭 csrf
                .csrf().disable()
                .logout().disable()
                // 不通过 session 获取 SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 放行所有请求
                .authorizeRequests()
                // 放行登录请求
                .antMatchers("/login").anonymous()
                // 放行所有请求
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.error("auth fail: {}", authException.getMessage());
                    ErrorResponseResult responseResult = ErrorResponseResult.result(HttpCodeEnum.AUTH_FAIL);
                    WebUtils.renderJSONString(response, responseResult);
                });
    }

}
