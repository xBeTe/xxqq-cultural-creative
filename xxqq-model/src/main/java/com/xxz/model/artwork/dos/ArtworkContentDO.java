package com.xxz.model.artwork.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

/**
 * @author xzxie
 * @create 2023/12/17 15:55
 */
@EqualsAndHashCode(callSuper = true)
@TableName("artwork_content")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkContentDO extends BaseDO {

    /*artwork_id  BIGINT(20) UNIQUE NOT NULL COMMENT '作品ID',
    content     varchar(255)      NOT NULL COMMENT '作品内容',
    enable_download TINYINT(1) UNSIGNED DEFAULT '1' COMMENT '是否允许下载: 0-不允许 1-允许',
    like_num    BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '点赞数',
    comment_num BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '评论数',
    collect_num BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '收藏数',*/
    /**
     * 作品ID
     */
    @TableField(value = "artwork_id")
    private Long artworkId;

    /**
     * 作品内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否允许下载
     */
    @TableField(value = "enable_download")
    private Boolean enableDownload;

    /**
     * 点赞数
     */
    @TableField(value = "like_num")
    private Long likeNum;

    /**
     * 评论数
     */
    @TableField(value = "comment_num")
    private Long commentNum;

    /**
     * 收藏数
     */
    @TableField(value = "collect_num")
    private Long collectNum;

}
