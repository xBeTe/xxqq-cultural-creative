package com.xxz.model.artwork.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxz.model.common.dos.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author xzxie
 * @create 2023/12/13 22:20
 */
@EqualsAndHashCode(callSuper = true)
@TableName("artwork_base")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkBaseDO extends BaseDO {

    /**
     * 作品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 作品封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 分类ID
     */
    @TableField(value = "classify_id")
    private Long classifyId;

    /**
     * 作品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 作品标签
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    private LocalDateTime publishTime;


}
