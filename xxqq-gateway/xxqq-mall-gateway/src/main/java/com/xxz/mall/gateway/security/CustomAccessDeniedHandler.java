package com.xxz.mall.gateway.security;

import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义授权失败处理器
 * @author xzxie
 * @create 2023/11/20 16:33
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        log.error("access denied: {}", denied.getMessage());
        ErrorResponseResult responseResult = ErrorResponseResult.result(HttpCodeEnum.NO_OPERATE_AUTH);
        return WebUtils.renderJSONString(response, responseResult);
    }
}
