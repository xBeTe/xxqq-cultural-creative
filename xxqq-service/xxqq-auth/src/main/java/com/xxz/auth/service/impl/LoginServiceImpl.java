package com.xxz.auth.service.impl;

import com.alibaba.fastjson2.JSON;
import com.xxz.auth.mapper.UserAccountMapper;
import com.xxz.auth.service.LoginService;
import com.xxz.common.cache.RedisUtils;
import com.xxz.common.constants.AuthConstants;
import com.xxz.common.jwt.JwtUtil;
import com.xxz.model.auth.LoginUserDetails;
import com.xxz.model.auth.dos.PermissionDO;
import com.xxz.model.auth.dos.UserAccountDO;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xzxie
 * @create 2023/11/14 21:01
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 用户登录
     * @param user 用户信息
     * @return 响应结果
     */
    @Override
    public ResponseResult login(LoginUserDTO user) {
        // 校验参数
        if (user == null) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        } else if (user.getUsername() == null) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_NAME_REQUIRE);
        } else if (user.getPassword() == null) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_PASSWORD_REQUIRE);
        }

        // 使用 AuthenticationManager 来进行认证
        Authentication authenticate = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticate = authenticationManager.authenticate(authenticationToken);

        // 登录失败
        if (Objects.isNull(authenticate)) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_FAIL);
        }

        // 登录成功
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // 获取用户信息
        LoginUserDetails userDetails = (LoginUserDetails) authenticate.getPrincipal();
        UserAccountDO userAccount = userDetails.getUserAccount();

        // 认证成功，返回 JWT token
        String token = jwtUtil.generateUserAuthToken(userAccount.getId(), userAccount.getUsername());
        // 将用户对应权限信息存入 redis
        List<String> permissions = userAccount.getPermissions().stream().map(PermissionDO::getName).collect(Collectors.toList());
        String key = AuthConstants.USER_PERMISSIONS_KEY_PREFIX + userAccount.getId();
        redisUtils.set(key, JSON.toJSONString(permissions));

        // 更新用户登录时间
        userAccount.setLastLoginTime(LocalDateTime.now());
        userAccountMapper.updateById(userAccount);

        return OkResponseResult.result().addMap(AuthConstants.TOKEN_RESPONSE_KEY, token);
    }

    /**
     * 退出登录
     * @param userId 用户id
     * @return 响应结果
     */
    @Override
    public ResponseResult logout(String userId) {
        // 校验参数
        if (StringUtils.isBlank(userId)) {
            return ErrorResponseResult.result(HttpCodeEnum.PARAM_INVALID);
        }

        // 删除 redis 中的用户权限信息
        String key = AuthConstants.USER_PERMISSIONS_KEY_PREFIX + userId;
        redisUtils.delete(key);

        return OkResponseResult.result("退出登录成功");
    }


}
