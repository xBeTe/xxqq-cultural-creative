package com.xxz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxz.common.JwtUtil;
import com.xxz.model.common.dtos.ErrorResponseResult;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.common.enums.HttpCodeEnum;
import com.xxz.model.config.JwtProperty;
import com.xxz.model.user.dtos.LoginDto;
import com.xxz.model.user.dtos.RegisterDto;
import com.xxz.model.user.pojos.User;
import com.xxz.user.config.JwtConfig;
import com.xxz.user.mapper.UserMapper;
import com.xxz.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author xzxie
 * @create 2023/11/7 17:37
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtProperty jwtProperty;

    /**
     * 登录
     * @param loginDto 登录信息
     * @return 返回登录成功的用户信息
     */
    @Override
    public ResponseResult login(LoginDto loginDto) {
        String loginName = loginDto.getLoginName();
        if (StringUtils.isEmpty(loginName)) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_NAME_REQUIRE);
        }

        String password = loginDto.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_PASSWORD_REQUIRE);
        }

        // 根据登录的用户名/手机号/邮箱 查询数据库用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, loginName)
                .or().eq(User::getEmail, loginName)
                .or().eq(User::getEmail, loginName);
        User dbUser = getOne(queryWrapper);
        if (dbUser == null) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_USER_NOT_EXIST);
        }

        // 获取数据存储的 salt，与登录密码一起加密后与数据库密码比较
        String salt = dbUser.getSalt();
        String shaPwd = DigestUtils.sha256Hex((password + salt).getBytes());
        if (!shaPwd.equals(dbUser.getPassword())) {
            return ErrorResponseResult.result(HttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // 生成 token
        JwtUtil.jwtConfig = jwtProperty;
        String token = JwtUtil.getToken(dbUser.getId().longValue(), dbUser.getName());
        dbUser.setPassword("");
        dbUser.setSalt("");
        return OkResponseResult.<User>result(dbUser)
                .addMap("token", token);
    }

    /**
     * 注册
     *
     * @param registerDto 注册信息
     * @return 返回注册成功的用户信息
     */
    @Override
    public ResponseResult register(RegisterDto registerDto) {
        String registerEmail = registerDto.getEmail();
        if (StringUtils.isBlank(registerEmail)) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_EMAIL_REQUIRE);
        }
        String registerPwd = registerDto.getPassword();
        if (StringUtils.isBlank(registerPwd)) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_PASSWORD_REQUIRE);
        }

        // 根据注册的邮箱 查询数据库用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, registerEmail);
        User dbUser = getOne(queryWrapper);
        if (dbUser != null) {
            return ErrorResponseResult.result(HttpCodeEnum.REGISTER_EMAIL_EXIST);
        }

        // 生成 salt
        String salt = DigestUtils.sha256Hex((registerEmail + System.currentTimeMillis()).getBytes()).substring(0, 8);
        log.info("salt: {}", salt);
        // 生成 sha256Hex 加密后的密码
        String shaPwd = DigestUtils.sha256Hex((registerPwd + salt).getBytes());
        log.info("shaPwd: {}", shaPwd);
        // 保存用户信息
        User user = new User();
        user.setName("user" + UUID.randomUUID().toString().substring(0, 8));
        user.setEmail(registerEmail);
        user.setPassword(shaPwd);
        user.setSalt(salt);
        user.setFlag(registerDto.getFlag());
        user.setCreatedTime(LocalDateTime.now());
        save(user);

        // 生成 token
        String token = JwtUtil.getToken(user.getId().longValue(), user.getName());
        user.setPassword("");
        user.setSalt("");
        return OkResponseResult.<User>result(user)
                .addMap("token", token);
    }

    /**
     * 根据 token 获取用户信息
     *
     * @param token token
     * @return 返回用户信息
     */
    @Override
    public ResponseResult getUserInfo(String token) {
        if (StringUtils.isBlank(token)) {
            return ErrorResponseResult.result(HttpCodeEnum.TOKEN_REQUIRE);
        }

        Claims claimsBody = JwtUtil.getClaimsBody(token);
        if (claimsBody == null) {
            return ErrorResponseResult.result(HttpCodeEnum.TOKEN_EXPIRE);
        }
        int res = JwtUtil.validateToken(token);
        if (res == 1 || res == 2) {
            return ErrorResponseResult.result(HttpCodeEnum.TOKEN_EXPIRE);
        }

        Integer userId = (Integer) claimsBody.get("id");
        User user = getById(userId);
        if (user == null) {
            return ErrorResponseResult.result(HttpCodeEnum.TOKEN_INVALID);
        }
        user.setPassword("");
        user.setSalt("");
        return OkResponseResult.<User>result(user);
    }
}
