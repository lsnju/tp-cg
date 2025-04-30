package com.lsnju.tp.cg.db;

import com.lsnju.base.model.BaseDo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/16 20:41
 * @version V1.0
 */
@Getter
@Setter
@Builder
public class BaseTableDo extends BaseDo {

    private String catalog;
    private String schema;
    private String tableName;
    private String tableType;
    private String tableComment;

}
