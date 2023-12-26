package com.xxz.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

/**
 * @author xzxie
 * @create 2023/12/13 21:50
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mall_user_fan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallUserFanDO extends BaseDO {

    @TableField("user_id")
    private Long userId;

    @TableField("follow_id")
    private Long fanId;

    @TableField("level")
    private Integer level;
}
