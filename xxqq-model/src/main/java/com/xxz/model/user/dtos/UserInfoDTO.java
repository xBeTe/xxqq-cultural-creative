package com.xxz.model.user.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xzxie
 * @create 2023/12/22 21:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户信息")
public class UserInfoDTO {

    private Long id;

    private String nickname;

    private String signature;

    private Long userTypeId;

    private String avatar;
}
