package com.xxz.model.auth.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xzxie
 * @create 2023/11/16 22:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class RoleDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 角色状态
     * {@link com.xxz.model.auth.enums.SimpleStatus}
     */
    @TableField(value = "status")
    private Short status;

    /**
     * 创建人
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 更新人
     */
    @TableField(value = "update_user_id")
    private Long updateUserId;





}
