package com.xxz.auth.controller;

import com.xxz.auth.service.LoginService;
import com.xxz.common.constants.GatewayConstants;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzxie
 * @create 2023/11/14 20:56
 */
// @RestController
@Api(tags = "用户登录接口")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginUserDTO user) {
        return loginService.login(user);
    }


    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public ResponseResult logout(@RequestHeader(GatewayConstants.HTTP_HEADER_USER_ID ) String userId) {
        return loginService.logout(userId);
    }
}
