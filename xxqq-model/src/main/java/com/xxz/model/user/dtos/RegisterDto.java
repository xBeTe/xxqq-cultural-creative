package com.xxz.model.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xzxie
 * @create 2023/7/5 16:13
 */
@Data
@ApiModel(value = "注册数据对象")
public class RegisterDto {

    @ApiModelProperty(value = "用户名", required = true)
    private String email;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "身份类型", required = true)
    private Short flag;

}
