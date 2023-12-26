package com.xxz.mall.gateway.config;

import com.xxz.common.constants.GatewayConstants;
import com.xxz.common.jwt.JwtUtil;
import com.xxz.mall.gateway.filter.CustomAccountAuthenticationToken;
import com.xxz.mall.gateway.filter.JwtAuthFilter;
import com.xxz.mall.gateway.security.CustomAuthorizationManager;
import com.xxz.mall.gateway.security.CustomSecurityContextRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xzxie
 * @create 2023/11/14 22:26
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ServerAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private ServerAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomSecurityContextRepository securityContextRepository;



    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeExchange()
                // 无需认证的请求路径
                .pathMatchers(GatewayConstants.EXCLUDED_AUTH_PATHS).permitAll()
                // 需要认证的请求路径
                .pathMatchers("/user/**").access(new CustomAuthorizationManager("mall:user"))
                .pathMatchers(HttpMethod.POST, "/user/type").access(new CustomAuthorizationManager( "admin:user"))
                .pathMatchers("/auth/logout").access(new CustomAuthorizationManager("mall:user", "admin:user"))
                .pathMatchers("/auth/account/password", "auth/account/register").access(new CustomAuthorizationManager("mall:email:verified"))
                .pathMatchers("/artwork/upload").access(new CustomAuthorizationManager("mall:user"))
                // .pathMatchers("/user/**").hasAuthority("mall:user")
                // .pathMatchers("/auth/password").hasAnyAuthority("mall:user", "mall:pwd")
                .anyExchange().permitAll()
                .and()
                // 支持跨域访问
                .csrf().disable()
                // 自定义过滤器
                .addFilterBefore(new JwtAuthFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_HEADERS_WRITER)
                // 自定义安全上下文
                .securityContextRepository(securityContextRepository )
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);



        return http.build();
    }
}
