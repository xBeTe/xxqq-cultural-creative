package com.xxz.model.artwork.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

/**
 * @author xzxie
 * @create 2023/12/14 16:39
 */
@EqualsAndHashCode(callSuper = true)
@TableName("artwork_classify")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkClassifyDO extends BaseDO {

    /*name        varchar(255)      NOT NULL COMMENT '分类名称',
    description varchar(255)      NOT NULL COMMENT '分类描述',
    status      TINYINT(1) DEFAULT '0' COMMENT '分类状态: 0-正常 1-禁用',
    order_num   TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '排序：0-255，越小越靠前',*/

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 分类状态: 0-正常 1-禁用
     * {@link com.xxz.model.auth.enums.SimpleStatus}
     */
    private Short status;

    /**
     * 排序：0-255，越小越靠前
     */
    private Short orderNum;
}
