package com.xxz.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

/**
 * @author xzxie
 * @create 2023/12/13 21:45
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mall_user_follow")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallUserFollowDO extends BaseDO {


    @TableField("user_id")
    private Long userId;

    @TableField("follow_id")
    private Long followId;

    @TableField("is_special")
    private Boolean specialFollowed;

    @TableField("level")
    private Integer level;


}
