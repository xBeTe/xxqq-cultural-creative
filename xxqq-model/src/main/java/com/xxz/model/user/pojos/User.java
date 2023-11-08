package com.xxz.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xzxie
 * @create 2023/11/1 15:41
 */
@Data
@TableName("mall_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @TableField("salt")
    private String salt;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;


    @TableField("image")
    private String image;

    /**
     * 0 男 1 女 2 未知
     */
    @TableField("status")
    private Short sex;

    /**
     * 0 未认证 1 已认证
     */
    @TableField("is_certification")
    private Boolean certification;

    /**
     * 0 未锁定 1 已锁定
     */
    @TableField("status")
    private Boolean status;

    /**
     *  1 设计师 2 学生 3 企业
     */
    @TableField("flag")
    private Short flag;


    @TableField("created_time")
    private LocalDateTime createdTime;



}
