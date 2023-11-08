package com.xxz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dtos.LoginDto;
import com.xxz.model.user.dtos.RegisterDto;
import com.xxz.model.user.pojos.User;

/**
 * @author xzxie
 * @create 2023/11/7 17:36
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param loginDto 登录信息
     * @return 返回登录成功的用户信息
     */
    ResponseResult login(LoginDto loginDto);

    /**
     * 注册
     * @param registerDto 注册信息
     * @return 返回注册成功的用户信息
     */
    ResponseResult register(RegisterDto registerDto);

    /**
     * 根据 token 获取用户信息
     * @param token token
     * @return 返回用户信息
     */
    ResponseResult getUserInfo(String token);
}
