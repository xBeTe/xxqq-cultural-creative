package com.xxz.mall.gateway.security;

import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义认证失败处理器
 *
 * @author xzxie
 * @create 2023/11/20 15:39
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.error("auth fail: {}", e.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        ErrorResponseResult responseResult = ErrorResponseResult.result(HttpCodeEnum.AUTH_FAIL);
        return WebUtils.renderJSONString(response, responseResult);
    }
}
