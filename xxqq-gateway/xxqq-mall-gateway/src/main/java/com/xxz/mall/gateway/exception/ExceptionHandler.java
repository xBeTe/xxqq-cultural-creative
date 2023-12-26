package com.xxz.mall.gateway.exception;

import com.xxz.common.exception.CustomException;
import com.xxz.common.jwt.JwtUtil;
import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xzxie
 * @create 2023/11/18 8:58
 */
@Component
@Slf4j
@Order(-1) // 优先级最高，最先执行
public class ExceptionHandler implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpResponse response = exchange.getResponse();
        log.error("exception: {}", ex);
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            ErrorResponseResult responseResult = ErrorResponseResult.result(customException.getHttpCodeEnum());
            return WebUtils.renderJSONString(response, responseResult);
        }


        return WebUtils.renderJSONString(response, ErrorResponseResult.result(HttpCodeEnum.SERVER_ERROR));
    }
}
