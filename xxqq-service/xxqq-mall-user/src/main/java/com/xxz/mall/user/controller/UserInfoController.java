package com.xxz.mall.user.controller;

import com.xxz.common.constants.GatewayConstants;
import com.xxz.common.constants.UserConstant;
import com.xxz.mall.user.service.UserInfoService;
import com.xxz.model.common.dtos.ResponseResult;
import com.xxz.model.user.dtos.UserInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xzxie
 * @create 2023/11/7 16:42
 */
@RestController
@Slf4j
@Api(tags = "用户信息接口")
@RequestMapping("/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    @ApiOperation("根据 token 获取用户信息")
    public ResponseResult getUserInfo(HttpServletRequest request) {
        String userId = request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID);
        return userInfoService.getUserInfo(userId);
    }

    @PutMapping
    @ApiOperation("更新用户信息")
    public ResponseResult updateUserInfo(HttpServletRequest request,
                                         UserInfoDTO userInfoDTO,
                                         @RequestParam(value = UserConstant.AVATAR_FORM_DATA_KEY, required = false) MultipartFile file
                                          ) {
        String userId = request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID);
        userInfoDTO.setId(Long.valueOf(userId));
        return userInfoService.updateUserInfo(userInfoDTO, file);
    }
}
