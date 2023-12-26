package com.xxz.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxz.auth.mapper.UserAccountMapper;
import com.xxz.common.exception.CustomException;
import com.xxz.model.auth.LoginUserDetails;
import com.xxz.model.auth.dos.PermissionDO;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzxie
 * @create 2023/11/14 20:42
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户
        UserAccountDO dbAccount = userAccountMapper.selectOne(Wrappers.<UserAccountDO>lambdaQuery()
                .eq(UserAccountDO::getUsername, username)
                .or().eq(UserAccountDO::getEmail, username));
        if (dbAccount == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("用户不存在");
        }

        // TODO 查询用户权限
        List<PermissionDO> permissions = new ArrayList<>();
        permissions.add(PermissionDO.builder().name("mall:user").build());
        permissions.add(PermissionDO.builder().name("admin:user").build());
        dbAccount.setPermissions(permissions);
        // 返回用户信息
        return new LoginUserDetails(dbAccount);
    }
}
