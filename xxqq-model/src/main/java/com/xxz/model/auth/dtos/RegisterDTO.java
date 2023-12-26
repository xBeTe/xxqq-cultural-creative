package com.xxz.model.auth.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xzxie
 * @create 2023/7/5 16:13
 */
@Data
@ApiModel(value = "注册数据对象")
public class RegisterDTO {


    @ApiModelProperty(value = "用户名", required = true)
    private String username;

}
