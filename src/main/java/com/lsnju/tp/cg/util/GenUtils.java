package com.lsnju.tp.cg.util;

import java.sql.JDBCType;
import java.util.Objects;

import com.google.common.base.CaseFormat;
import com.lsnju.tp.cg.model.enums.ColTypeEnum;

/**
 *
 * @author ls
 * @since 2023/8/18 6:06
 * @version V1.0
 */
public class GenUtils {

    public static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    public static String getUpCaseName(String lowerUnder) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, lowerUnder);
    }

    public static String getLowCaseName(String lowerUnder) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, lowerUnder);
    }

    public static ColTypeEnum getJavaType(JDBCType jdbcType) {
        Objects.requireNonNull(jdbcType);
        switch (jdbcType) {
            case BIT:
            case BOOLEAN:
                return ColTypeEnum.BOOL;
            case TINYINT:
                return ColTypeEnum.BYTE;
            case SMALLINT:
                return ColTypeEnum.SHORT;
            case INTEGER:
                return ColTypeEnum.INT;
            case BIGINT:
                return ColTypeEnum.LONG;
            case REAL:
            case FLOAT:
                return ColTypeEnum.FLOAT;
            case DOUBLE:
                return ColTypeEnum.DOUBLE;
            case NUMERIC:
            case DECIMAL:
                return ColTypeEnum.NUMERIC;
            case DATE:
            case TIME:
            case TIMESTAMP:
            case TIME_WITH_TIMEZONE:
            case TIMESTAMP_WITH_TIMEZONE:
                return ColTypeEnum.DATE;
            case CHAR:
            case VARCHAR:
            case LONGVARCHAR:
            case LONGNVARCHAR:
                return ColTypeEnum.STRING;
            case BINARY:
            case VARBINARY:
            case LONGVARBINARY:
                return ColTypeEnum.BYTE_ARRAY;
            default:
        }
        return ColTypeEnum.STRING;
    }

}
