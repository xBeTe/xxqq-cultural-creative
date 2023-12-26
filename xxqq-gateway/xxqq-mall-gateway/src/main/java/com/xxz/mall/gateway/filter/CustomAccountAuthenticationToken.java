package com.xxz.mall.gateway.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author xzxie
 * @create 2023/11/24 22:08
 */
public class CustomAccountAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Object principal;
    private final Object credentials;

    public CustomAccountAuthenticationToken(Object credentials, String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.setAuthenticated(true);
        this.credentials = credentials;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
