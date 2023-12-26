package com.xxz.model.auth.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author xzxie
 * @create 2023/11/10 17:07
 */
@Builder
@Data
@ApiModel(value = "邮箱验证码数据对象")
public class MailVerificationDTO {

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;




}
