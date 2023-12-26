package com.xxz.model.auth.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xzxie
 * @create 2023/11/14 21:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户登录DTO")
public class LoginUserDTO {

    @ApiModelProperty("用户名/邮箱")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
