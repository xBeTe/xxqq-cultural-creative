-- 商城用户数据库

-- 创建商城用户数据库
CREATE DATABASE IF NOT EXISTS `xxqq_mall_user` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE `xxqq_mall_user`;

DROP TABLE IF EXISTS `mall_user_type`;
DROP TABLE IF EXISTS `mall_user_info`;
DROP TABLE IF EXISTS `mall_user_follow`;
DROP TABLE IF EXISTS `mall_user_fan`;

-- 创建用户类型表
CREATE TABLE `mall_user_type`
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    value       VARCHAR(20)         NOT NULL COMMENT '用户类型',
    label       VARCHAR(20)         NOT NULL COMMENT '用户类型标签',
    icon        VARCHAR(50)         DEFAULT NULL COMMENT '用户类型图标',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`value`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商城用户类型表';

-- 创建用户信息
CREATE TABLE `mall_user_info`
(
    id               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id          BIGINT(20)          NOT NULL COMMENT '用户ID',
    username         VARCHAR(20)         DEFAULT NULL COMMENT '用户名',
    nickname         VARCHAR(20)         DEFAULT NULL COMMENT '昵称',
    signature        VARCHAR(100)        DEFAULT NULL COMMENT '个性签名',
    gender           TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '性别: 0-未知 1-男 2-女',
    birthday         DATE                DEFAULT NULL COMMENT '生日',
    avatar           VARCHAR(255)        DEFAULT NULL COMMENT '头像',
    user_type_id     BIGINT(20)          NOT NULL COMMENT '用户类型 id',
    phone            VARCHAR(20)         DEFAULT NULL COMMENT '手机号',
    is_certification TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否认证: 0-未认证 1-已认证',
    is_deleted       TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time      DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time      DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`user_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商城用户信息表';

-- 创建用户关注表
CREATE TABLE `mall_user_follow`
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户id',
    follow_id   BIGINT(20)          NOT NULL COMMENT '关注用户id',
    is_special  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否特别关注: 0-否 1-是',
    level       TINYINT UNSIGNED    DEFAULT '0' COMMENT '关注度',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`user_id`, `follow_id`),
    FOREIGN KEY (`user_id`) REFERENCES `mall_user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`follow_id`) REFERENCES `mall_user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商城用户关注表';

-- 创建用户粉丝表
CREATE TABLE `mall_user_fan`
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户id',
    fan_id      BIGINT(20)          NOT NULL COMMENT '粉丝用户id',
    level       TINYINT UNSIGNED    DEFAULT '0' COMMENT '粉丝忠实度',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE (`user_id`, `fan_id`),
    FOREIGN KEY (`user_id`) REFERENCES `mall_user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`fan_id`) REFERENCES `mall_user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商城用户粉丝表';





