package com.xxz.auth.controller;

import com.xxz.auth.service.AccountService;
import com.xxz.common.constants.AuthConstants;
import com.xxz.common.constants.GatewayConstants;
import com.xxz.model.auth.dtos.LoginUserDTO;
import com.xxz.model.auth.dtos.RegisterDTO;
import com.xxz.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzxie
 * @create 2023/11/24 11:14
 */
@RestController
@Api(tags = "用户账户接口")
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation("检查用户名是否可用")
    @GetMapping("/check/username/{username}")
    public ResponseResult checkUsername(@PathVariable String username) {
        return accountService.checkUsername(username);
    }

    @ApiOperation("设置密码")
    @PutMapping("/password")
    public ResponseResult setPassword(@RequestHeader(GatewayConstants.HTTP_HEADER_USER_ID) String userId, @RequestBody LoginUserDTO dto) {
        return accountService.updatePassword(userId, dto);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestHeader(GatewayConstants.HTTP_HEADER_USER_ID) String userId, @RequestBody RegisterDTO dto) {
        return accountService.register(userId, dto);
    }

}
