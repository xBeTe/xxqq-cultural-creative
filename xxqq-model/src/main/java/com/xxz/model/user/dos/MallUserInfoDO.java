package com.xxz.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import com.xxz.model.user.enums.Gender;
import com.xxz.model.user.enums.UserType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xzxie
 * @create 2023/11/1 15:41
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mall_user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallUserInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户 id，auth 微服务生成
     */
    @TableField("user_id")
    private Long userId;


    /**
     * 用户名（唯一） 来自 auth 微服务
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 性别: 0-未知 1-男 2-女
     * {@link Gender}
     */
    @TableField("gender")
    private Short gender;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 用户头像路径
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 用户类别
     * {@link UserType}
     */
    @TableField("user_type_id")
    private Long userTypeId;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 0 未认证 1 已认证
     */
    @TableField("is_certification")
    private Boolean certification;


}
