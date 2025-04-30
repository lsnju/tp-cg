package com.lsnju.tp.cg.model.enums;

import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author lisong
 * @since 2023/9/8 11:22
 * @version V1.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DbTypeEnum {

    PostgreSQL("PostgreSQL", "PostgreSQL"),
    MariaDB("MariaDB", "MariaDB"),
    MySQL("MySQL", "MySQL"),

    // ...
    ;

    private final String code;
    private final String desc;

    public static DbTypeEnum getByCode(String code) {
        return Stream.of(values()).filter(i -> i.code.equals(code)).findAny().orElse(null);
    }
}
