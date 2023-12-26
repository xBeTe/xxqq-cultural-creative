package com.xxz.common.util;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author xzxie
 * @create 2023/11/17 17:01
 */
@Slf4j
public class WebUtils {


    /**
     * 在 webflux 中返回 json 字符串
     * @param response http 响应
     * @param object 返回的对象
     * @return Mono<Void>
     */
    public static Mono<Void> renderJSONString(ServerHttpResponse response, Object object) {
        String jsonString = JSON.toJSONString(object);
        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 在 spring mvc 中返回 json 字符串
     * @param response http 响应
     * @param object 返回的对象
     */
    public static void renderJSONString(HttpServletResponse response, Object object) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(object));

        } catch (IOException e) {
            log.error("renderJSONString error", e);
        }
    }
}
