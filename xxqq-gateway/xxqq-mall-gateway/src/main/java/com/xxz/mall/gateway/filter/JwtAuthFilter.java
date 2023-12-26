package com.xxz.mall.gateway.filter;

import com.xxz.common.constants.AuthConstants;
import com.xxz.common.constants.GatewayConstants;
import com.xxz.common.constants.JwtConstants;
import com.xxz.common.jwt.JwtUtil;
import com.xxz.common.util.WebUtils;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author xzxie
 * @create 2023/11/20 9:41
 */
@Slf4j
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 构造方法，不能将该过滤器注入到容器中，否则在 spring security 中执行一次，在 webflux 中又执行一次
     * @param jwtUtil
     */
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 过滤器的逻辑
     * @param exchange 交换机
     * @param chain 过滤器链
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {


        // 获取 request 和 response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestURI = request.getURI().getPath();
        log.info("request url: {}",requestURI);

        // 判断本次请求是否需要请求
        boolean check = check(GatewayConstants.EXCLUDED_AUTH_PATHS, requestURI);

        // 如果不需要处理，则直接放行
        if (check) {
            log.info("request {} ignore token", requestURI);
            return chain.filter(exchange);
        }

        // 获取 token
        String token = request.getHeaders().getFirst(GatewayConstants.HTTP_HEADER_TOKEN);
        if (StringUtils.isBlank(token)) {
            log.info("token is empty");
            // 放行请求，交由下游处理
            return chain.filter(exchange);
        }
        log.info("token: {}", token);

        // 判断 token 是否有效
        try {

            // 判断 token 是否过期
            int res = jwtUtil.validateToken(token);
            // -1：有效且需要刷新，0：有效，1：过期，2：无效
            if (res == 1) {
                return WebUtils.renderJSONString(response, HttpCodeEnum.TOKEN_INVALID);
            } else if (res == 2) {
                return WebUtils.renderJSONString(response, HttpCodeEnum.TOKEN_EXPIRED);
            } else if (res == -1) {
                // 需要刷新 token
                OkResponseResult<Object> result = OkResponseResult.result(HttpCodeEnum.TOKEN_REFRESH_RETRY).addMap(AuthConstants.TOKEN_RESPONSE_KEY, jwtUtil.refreshToken(token));
                return WebUtils.renderJSONString(response, result);
            }

            //  获取用户id
            Claims claimsBody = jwtUtil.getClaimsBody(token);
            Long userId = claimsBody.get(JwtConstants.CLAIM_KEY_USER_ID, Long.class);
            log.info("user id: {}", userId);
            String username = claimsBody.get(JwtConstants.CLAIM_KEY_USERNAME, String.class);
            log.info("username: {}", username);
            // 获取 token 主题
            String subject = claimsBody.getSubject();
            // 将用户 id、username 和 token 主题放入到header中
            ServerHttpRequest serverHttpRequest = request.mutate().headers(
                    httpHeaders -> {
                        httpHeaders.add(GatewayConstants.HTTP_HEADER_USER_ID, String.valueOf(userId));
                        httpHeaders.add(GatewayConstants.HTTP_HEADER_USERNAME, username);
                        httpHeaders.add(GatewayConstants.HTTP_HEADER_TOKEN_SUBJECT, subject);
                    }
            ).build();
            // 重置请求
            exchange.mutate().request(serverHttpRequest);

        } catch (Exception e) {
            log.error("token is invalid: {}", e.getMessage());
            throw e;
        }

        // 放行
        return chain.filter(exchange);

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
