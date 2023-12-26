-- 作品数据库

-- 创建作品数据库
CREATE DATABASE IF NOT EXISTS `xxqq_artwork` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `xxqq_artwork`;

-- 创建作品分类表
DROP TABLE IF EXISTS `artwork_classify`;
CREATE TABLE `artwork_classify`
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '分类id',
    name        varchar(255) NOT NULL COMMENT '分类名称',
    description varchar(255) NOT NULL COMMENT '分类描述',
    status      TINYINT(1)          DEFAULT '0' COMMENT '分类状态: 0-正常 1-禁用',
    order_num   TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '排序：0-255，越小越靠前',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='作品分类表';

-- 创建作品表
DROP TABLE IF EXISTS `artwork_base`;
CREATE TABLE `artwork_base`
(
    id           BIGINT(20)        NOT NULL AUTO_INCREMENT COMMENT '作品id',
    name         varchar(20)       NOT NULL COMMENT '作品名称',
    cover        varchar(255)      NOT NULL COMMENT '作品封面',
    user_id      BIGINT(20)        NOT NULL COMMENT '用户ID',
    author       varchar(20)       NOT NULL COMMENT '作者',
    classify_id  BIGINT(20)        NOT NULL COMMENT '分类ID',
    description  varchar(255)      NOT NULL COMMENT '作品描述',
    tags         varchar(255)      NOT NULL COMMENT '作品标签',
    publish_time datetime          NOT NULL COMMENT '发布时间',
    is_deleted   TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time  datetime          DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time  datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`classify_id`) REFERENCES `artwork_classify` (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='作品表';

-- 创建作品内容表
DROP TABLE IF EXISTS `artwork_content`;
CREATE TABLE `artwork_content`
(
    id          BIGINT(20)        NOT NULL AUTO_INCREMENT COMMENT '作品内容id',
    artwork_id  BIGINT(20) UNIQUE NOT NULL COMMENT '作品ID',
    content     varchar(255)      NOT NULL COMMENT '作品内容',
    enable_download TINYINT(1) UNSIGNED DEFAULT '1' COMMENT '是否允许下载: 0-不允许 1-允许',
    like_num    BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '点赞数',
    comment_num BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '评论数',
    collect_num BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '收藏数',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time datetime          DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`artwork_id`) REFERENCES `artwork_base` (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='作品内容表';


-- 创建作品评论表
DROP TABLE IF EXISTS `artwork_comment`;
CREATE TABLE `artwork_comment`
(
    id          BIGINT(20)        NOT NULL AUTO_INCREMENT COMMENT '评论id',
    artwork_id  BIGINT(20)        NOT NULL COMMENT '作品ID',
    user_id     BIGINT(20)        NOT NULL COMMENT '用户ID',
    content     varchar(255)      NOT NULL COMMENT '评论内容',
    like_num    BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '点赞数',
    is_deleted  TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time datetime          DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`artwork_id`) REFERENCES `artwork_base` (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT ='作品评论表';

