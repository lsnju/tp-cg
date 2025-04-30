package com.lsnju.tp.cg.db;

import java.sql.JDBCType;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.lsnju.base.model.BaseDo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/16 20:43
 * @version V1.0
 */
@Getter
@Setter
@Builder
public class BaseColumnDo extends BaseDo {

    @ToStringExclude
    private String catalog;
    @ToStringExclude
    private String schema;
    private String tableName;
    private int orderNo;
    private String columnName;
    private String columnType;
    private String columnComment;
    @ToStringExclude
    private String columnKey;
    private JDBCType dataType;
    private int maxLength;
    @ToStringExclude
    private int precision;
    @ToStringExclude
    private int scale;
    private String nullable;
    private String defaultValue;
    private boolean autoIncrement;

}
