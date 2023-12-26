package com.xxz.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/12/13 21:27
 */
@AllArgsConstructor
@Getter
public enum Gender {

    UNKNOWN((short) 0, "未知"),
    MALE((short) 1, "男性"),
    FEMALE((short) 2, "女性");

    private final short code;
    private final String desc;
}
