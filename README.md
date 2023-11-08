# xxqq-cultural-creative

> 项目说明：

## 数据库与表

### 1. `xxqq-user`：商城用户数据库

- `mall-user`：用户基本信息表

```sql
create table mall_user
(
    id               int unsigned auto_increment comment '主键'
        primary key,
    salt             varchar(32)      null comment '密码、通信等加密盐',
    name             varchar(20)      null comment '用户名',
    password         varchar(256)     null comment '密码,SHA256加密',
    phone            varchar(11)      null comment '手机号',
    email            varchar(50)      null comment '邮箱',
    image            varchar(255)     null comment '头像',
    sex              tinyint unsigned null comment '0 男
            1 女
            2 未知',
    is_certification tinyint unsigned null comment '0 未
            1 是',
    status           tinyint unsigned null comment '0正常
            1锁定',
    flag             tinyint unsigned null comment '0 普通用户
            1 设计师
            2 学生
    		3 企业',
    created_time     datetime         null comment '注册时间'
)
    comment '用户信息表' collate = utf8mb4_unicode_ci
                         row_format = DYNAMIC;


```



## 服务端口号

### 1. 网关

| Gateway | Port  | Description          |
| ------- | ----- | -------------------- |
| mall    | 11801 | 文创商城网关         |
| admin   | 11802 | 文创商城后台管理系统 |
|         |       |                      |

### 2. 微服务

| Services | Port  | Description |
| -------- | ----- | ----------- |
| user     | 11901 | 用户微服务  |
| artwork  | 11902 | 作品微服务  |
| search   | 11903 | 搜索微服务  |

## 响应状态码

- 成功：200

| Type    | Code | Message  |
| ------- | ---- | -------- |
| SUCCESS | 200  | 操作成功 |

- 登录：1 ~ 50

| Type                 | Code | Message  |
| -------------------- | ---- | -------- |
| NEED_LOGIN           | 1    | 需要登录 |
| LOGIN_PASSWORD_ERROR | 2    | 密码错误 |

- TOKEN：50 ~ 100

| Type          | Code | Message        |
| ------------- | ---- | -------------- |
| TOKEN_INVALID | 50   | 无效的 TOKEN   |
| TOKEN_EXPIRE  | 51   | TOKEN 已过期   |
| TOKEN_REQUIRE | 52   | TOKEN 是必须的 |

- 参数错误：500 ~ 1000

| Type                     | Code | Message        |
| ------------------------ | ---- | -------------- |
| PARAM_REQUIRE            | 500  | 参数错误       |
| PARAM_INVALID            | 501  | 无效参数       |
| PARAM_IMAGE_FORMAT_ERROR | 502  | 图片格式有误   |
| PARAM_FILE_FORMAT_ERROR  | 503  | 文件格式有误   |
| SERVER_ERROR             | 504  | 服务器内部错误 |

- 数据错误

| Type                 | Code | Message         |
| -------------------- | ---- | --------------- |
| DATA_EXIST           | 1000 | 数据已经存在    |
| DATA_NOT_EXIST       | 1001 | 数据不存在      |
| DATA_SAVE_FAIL       | 1002 | 数据保存失败    |
| USER_DATA_NOT_EXIST  | 1010 | User数据不存在  |
| ADMIN_DATA_NOT_EXIST | 1011 | Admin数据不存在 |

- 权限错误：3501 ~ 3600

| Type             | Code  | Message        |
| ---------------- | ----- | -------------- |
| NO_OPERATOR_AUTH | 3000  | 无权限操作     |
| NEED_ADMIN       | 30001 | 需要管理员权限 |

- 作品错误：3501 ~ 3600

| Type                    | Code | Message      |
| ----------------------- | ---- | ------------ |
| MATERIAL_REFERENCE_FAIL | 3501 | 素材引用失败 |
| ARTWORK_NOT_EXIST       | 3502 | 作品不存在   |
| ARTWORK_SAVE_FAIL       | 3503 | 作品保存失败 |
| ARTWORK_STATUS_ERROR    | 3504 | 作品状态错误 |

