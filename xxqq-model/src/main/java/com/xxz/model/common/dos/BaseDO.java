package com.xxz.model.common.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库对象基类
 *
 * @author xzxie
 * @create 2023/11/16 22:11
 */
@Data
public class BaseDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 是否删除
     */
    @JsonIgnore
    @TableField(value = "is_deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}
