package com.xxz.user.controller;

import com.xxz.model.common.dtos.OkResponseResult;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dtos.LoginDto;
import com.xxz.model.user.dtos.RegisterDto;
import com.xxz.model.user.pojos.User;
import com.xxz.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzxie
 * @create 2023/11/7 16:42
 */
@RestController
@Slf4j
@Api(tags = "用户登录注册接口")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseResult register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("根据 token 获取用户信息")
    public ResponseResult getUserInfo(@RequestHeader("token") String token) {
        return userService.getUserInfo(token);
    }
}
