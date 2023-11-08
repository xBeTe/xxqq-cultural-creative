package com.xxz.model.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xzxie
 * @create 2023/7/5 16:12
 */
@Data
@ApiModel(value = "登录数据对象")
public class LoginDto {

    /**
     * 前端传来的登录名字段，可能为用户名，可能为邮箱，可能为手机号
     */
    @ApiModelProperty(value = "登录名(用户名/手机号/邮箱)", required = true)
    private String loginName;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
