package com.xxz.model.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.auth.enums.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xzxie
 * @create 2023/11/15 20:34
 */
@Data
@NoArgsConstructor
public class LoginUserDetails implements UserDetails {

    private UserAccountDO userAccount;

    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public LoginUserDetails(UserAccountDO userAccountDO) {
        this.userAccount = userAccountDO;
        this.authorities = userAccountDO.getPermissions().stream().map(permission -> {
            return new SimpleGrantedAuthority(permission.getName());
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.equals(this.userAccount.getStatus(), AccountStatus.NORMAL.getCode());
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(this.userAccount.getStatus(), AccountStatus.NORMAL.getCode());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.userAccount.getStatus(), AccountStatus.NORMAL.getCode());
    }
}
