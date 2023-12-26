package com.xxz.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/12/12 22:35
 */
@Getter
@AllArgsConstructor
public enum UserType {
    DESIGNER("DES", "设计师"),
    STUDENT("STU", "设计师"),
    BUSINESS("BUS", "设计师");

    private final String code;
    private final String desc;


}
