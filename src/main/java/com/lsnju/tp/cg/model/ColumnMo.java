package com.lsnju.tp.cg.model;

import java.sql.JDBCType;

import com.lsnju.base.model.BaseMo;
import com.lsnju.tp.cg.model.enums.ColTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/17 16:56
 * @version V1.0
 */
@Getter
@Setter
public class ColumnMo extends BaseMo {

    private int orderNo;
    private String columnName;
    private String columnType;
    private String columnComment;
    private JDBCType dataType;
    private String nullable;
    private String defaultValue;
    private int maxLength;
    private boolean autoIncrement;
    private boolean now;

    private String propName;
    private ColTypeEnum propType;
    private String propGetMethod;
    private String propSetMethod;

}
