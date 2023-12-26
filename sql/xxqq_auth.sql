/*
 xxqq-auth 认证中心对应数据库脚本
 存储包括：商城用户和后台管理用户的账户信息、用户的角色和权限信息
 */
# 创建认证中心数据库数据库
CREATE DATABASE IF NOT EXISTS `xxqq_auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `xxqq_auth`;
-- 创建商城账号表
DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account`
(
    id              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    username        VARCHAR(20)  DEFAULT NULL COMMENT '用户名',
    password        VARCHAR(256) DEFAULT NULL COMMENT '加密后的密码',
    email           VARCHAR(50)         NOT NULL COMMENT '邮箱',
    status          TINYINT(1)   DEFAULT '0' COMMENT '账号状态: 0-未激活 1-正常 2-已锁定 3-已注销',
    is_deleted      TINYINT(1)   DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    last_login_time DATETIME     DEFAULT NULL COMMENT '最后登录时间'

)
    ENGINE = INNODB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = DYNAMIC COMMENT ='用户账号表';


-- 创建角色表
DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id           BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) UNIQUE NOT NULL,
    status       TINYINT(1)          DEFAULT '1' COMMENT '角色状态: 0-禁用 1-启用',
    is_deleted   TINYINT(1)          DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    description  VARCHAR(100),
    created_time DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_user  BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '创建人',
    update_time  DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_user  BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '更新人',
    update_log   VARCHAR(100)        DEFAULT NULL COMMENT '更新日志',
    FOREIGN KEY (create_user) REFERENCES user_account (id),
    FOREIGN KEY (update_user) REFERENCES user_account (id)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT ='角色表';

-- 创建权限表`
DROP TABLE IF EXISTS permission;
CREATE TABLE permission
(
    id           BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) UNIQUE NOT NULL,
    status       TINYINT(1)          DEFAULT '1' COMMENT '角色状态: 0-禁用 1-启用',
    is_deleted   TINYINT(1)          DEFAULT '0' COMMENT '是否删除: 0-未删除 1-已删除',
    description  VARCHAR(100),
    created_time DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_user  BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '创建人',
    update_time  DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_user  BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '更新人',
    update_log   VARCHAR(100)        DEFAULT NULL COMMENT '更新日志',
    FOREIGN KEY (create_user) REFERENCES user_account (id),
    FOREIGN KEY (update_user) REFERENCES user_account (id)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT ='权限表';

-- 创建用户角色映射表
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    user_id BIGINT(20) UNSIGNED NOT NULL,
    role_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user_account (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT ='用户权限映射表';

-- 创建角色权限映射表
DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission
(
    role_id       BIGINT(20) UNSIGNED NOT NULL,
    permission_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT ='角色权限映射表';