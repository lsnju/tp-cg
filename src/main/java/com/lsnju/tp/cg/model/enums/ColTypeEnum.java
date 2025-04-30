package com.lsnju.tp.cg.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author ls
 * @since 2023-08-18 15:32:55
 * @version V1.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ColTypeEnum {

    BOOL("boolean", "true", "Boolean"),
    BYTE("byte", "1", "Byte"),
    SHORT("short", "1", "Short"),
    INT("int", "1", "Integer"),
    LONG("long", "1", "Long"),

    FLOAT("float", "0", "Float"),
    DOUBLE("double", "0", "Double"),

    STRING("String", "\"a\"", "String"),
    NUMERIC("java.math.BigDecimal", "new java.math.BigDecimal(1)", "java.math.BigDecimal"),
    DATE("java.util.Date", "new java.util.Date()", "java.util.Date"),

    BYTE_ARRAY("byte[]", "new byte[0]", "byte[]"),

    // ..
    ;

    private final String type;
    private final String defaultValue;
    private final String mpType;

}
