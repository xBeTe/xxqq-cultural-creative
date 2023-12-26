package com.xxz.model.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xzxie
 * @create 2023/11/16 22:28
 */
@Getter
@AllArgsConstructor
public enum SimpleStatus {

    NORMAL((short) 0, "正常"),
    DELETED((short) 1, "已删除");

    private final Short code;
    private final String desc;
}
