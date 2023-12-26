package com.xxz.auth.service;

import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.common.dtos.ResponseResult;

/**
 * @author xzxie
 * @create 2023/11/14 21:00
 */
public interface LoginService {
    /**
     * 用户登录
     * @param user 用户信息
     * @return 包含 token 的相应结果
     */
    ResponseResult login(LoginUserDTO user);


    /**
     * 用户登出
     * @param userId 用户id
     * @return 响应结果
     */
    ResponseResult logout(String userId);
}
