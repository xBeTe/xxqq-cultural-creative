package com.xxz.mall.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.xxz.common.cache.RedisUtils;
import com.xxz.common.constants.AuthConstants;
import com.xxz.common.constants.GatewayConstants;
import com.xxz.common.constants.JwtConstants;
import com.xxz.common.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Jwt 认证转换器
 *
 * @author xzxie
 * @create 2023/11/17 22:34
 */
@Slf4j
// @Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    // @Autowired
    private JwtUtil jwtUtil;

    // @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        List<SimpleGrantedAuthority> authorities;

        // 获取 用户 id
        String id = request.getHeaders().getFirst(GatewayConstants.HTTP_HEADER_USER_ID);
        log.info("user id: {}", id);

        // 判断 token 是否存在
        if (StringUtils.isBlank(id)) {
            log.error("user id is empty");
            // 结束请求
            return Mono.empty();
        }

        // 获取 用户 id
        String tokenSubject = request.getHeaders().getFirst(GatewayConstants.HTTP_HEADER_TOKEN_SUBJECT);
        log.info("token subject: {}", tokenSubject);

        // 判断 token 是否存在
        if (StringUtils.isBlank(tokenSubject)) {
            log.error("token subject is empty");
            // 结束请求
            return Mono.empty();
        }

        Authentication authentication;

        // 获取用户权限
        String key = AuthConstants.USER_PERMISSIONS_KEY_PREFIX + id;
        List<String> permissions = JSON.parseArray(redisUtils.get(key), String.class);
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        if (JwtConstants.TOKEN_SUBJECT_EMAIL_VERIFIED.equals(tokenSubject)) {
            authorities.add(new SimpleGrantedAuthority("mall:email:verified"));
        }


        authentication = new CustomAccountAuthenticationToken(null, null, authorities);

        return Mono.just(authentication);

    }
}
