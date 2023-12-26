package com.xxz.mall.gateway.security;

import com.xxz.common.exception.CustomException;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义授权管理器
 *
 * @author xzxie
 * @create 2023/11/19 20:56
 */
@Slf4j
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private List<String> authorities = new ArrayList<>();


    public CustomAuthorizationManager(String authority, String... authorities) {
        this.authorities.add(authority);
        if (authorities != null) {
            this.authorities.addAll(Arrays.asList(authorities));
        }
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    log.info("role: {}, authorities: {}", role, authorities);
                    if (authorities.contains(role)) {
                        return true;
                    }
                    throw new CustomException(HttpCodeEnum.NO_OPERATE_AUTH);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

    }

}
