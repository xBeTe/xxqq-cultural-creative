package com.xxz.model.auth.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xzxie
 * @create 2023/11/16 21:16
 */
@EqualsAndHashCode(callSuper = true)
@TableName("user_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;


    /**
     * 手机号
     */
    @TableField(value = "email")
    private String email;


    /**
     * 账号状态: 0-未激活 1-正常 2-已锁定 3-已注销
     * {@link com.xxz.model.auth.enums.AccountStatus}
     */
    @TableField(value = "status")
    private Short status;

    /**
     * 上次登录时间
     */
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;
    /**
     * 用户角色
     */
    @TableField(exist = false)
    private List<PermissionDO> permissions;


}
