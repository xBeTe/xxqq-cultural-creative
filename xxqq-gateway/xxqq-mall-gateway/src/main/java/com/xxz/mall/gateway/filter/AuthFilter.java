package com.xxz.mall.gateway.filter;

import com.xxz.common.JwtUtil;
import com.xxz.mall.gateway.config.JwtConfig;
import com.xxz.model.config.JwtProperty;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xzxie
 * @create 2023/11/6 10:58
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    // 路径匹配器，支持通配符
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final String USER_ID_HEADER = "userId";
    private static final String TOKEN_HEADER = "token";

    @Autowired
    private JwtProperty jwtProperty;

    /**
     * 过滤器的逻辑
     * @param exchange 交换机
     * @param chain 过滤器链
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取 request 和 response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/user/login",
                "/user/register",
                "/user/doc.html",
                "/user/v2/api-docs-ext",
                "/user/swagger-resources",
                "/user/v2/api-docs",
                "/user/swagger-ui.html",
                "/user/swagger-resources/configuration/ui",
                "/user/swagger-resources/configuration/security",
        };

        log.info("request uil: {}", request.getURI().getPath());
        if (check(urls, request.getURI().getPath())) {
            // 放行
            return chain.filter(exchange);
        }

        // 获取 token
        String token = request.getHeaders().getFirst(TOKEN_HEADER);

        // 判断 token 是否存在
        if (StringUtils.isBlank(token)) {
            // 不存在，返回 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 结束请求
            return response.setComplete();
        }

        // 判断 token 是否有效
        try {
            JwtUtil.jwtConfig = jwtProperty;
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            // 判断 token 是否过期
            int res = JwtUtil.validateToken(token);
            // -1：有效且需要刷新，0：有效，1：过期，2：无效
            if (res == 1 || res == 2) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            } else if (res == -1){
                // TODO 刷新TOKEN
            }

            //  获取用户id
            Object id = claimsBody.get(JwtUtil.CLAIM_KEY_USER_ID);
            // 将用户id放入到header中
            ServerHttpRequest serverHttpRequest = request.mutate().headers(
                    httpHeaders -> httpHeaders.add(USER_ID_HEADER, id.toString())
            ).build();
            // 重置请求
            exchange.mutate().request(serverHttpRequest);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 放行
        return chain.filter(exchange);
    }

    /**
     * 过滤器的优先级，数字越小，优先级越高
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls       本次请求的 URI
     * @param requestURI 不需要处理的请求路径
     * @return 路径匹配返回 true，否则返回 false
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
