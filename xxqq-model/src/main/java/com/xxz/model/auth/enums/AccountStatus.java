package com.xxz.model.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/11/16 22:24
 */
@Getter
@AllArgsConstructor
public enum AccountStatus {
    INACTIVE((short) 0, "未激活"),
    NORMAL((short) 1, "正常"),
    LOCKED((short) 2, "封禁"),
    CANCELLATION((short) 3, "注销");

    private final Short code;
    private final String desc;


}
