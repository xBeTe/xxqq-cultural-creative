package com.xxz.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

/**
 * @author xzxie
 * @create 2023/12/19 20:19
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mall_user_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallUserTypeDO extends BaseDO {

    /**
     * 用户类型
     */
    @TableField("value")
    private String value;

    /**
     * 用户类型名称
     */
    @TableField("label")
    private String label;

    /**
     * 用户类型图标
     */
    @TableField("icon")
    private String icon;
}
