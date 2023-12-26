package com.xxz.mall.user.controller;

import com.xxz.mall.user.service.UserTypeService;
import com.xxz.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzxie
 * @create 2023/12/19 20:32
 */
@RestController
@RequestMapping("/type")
@Api(tags = "用户类型接口")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @GetMapping("/list")
    @ApiOperation("获取用户类型")
    public ResponseResult getUserType() {
        return userTypeService.getUserType();
    }



}
