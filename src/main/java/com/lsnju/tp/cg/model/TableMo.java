package com.lsnju.tp.cg.model;

import java.util.List;

import com.lsnju.base.model.BaseMo;
import com.lsnju.tp.cg.model.res.DaoMo;
import com.lsnju.tp.cg.model.res.RepoMo;
import com.lsnju.tp.cg.model.res.RestMo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/17 16:52
 * @version V1.0
 */
@Getter
@Setter
public class TableMo extends BaseMo {

    private DbMo dbMo;
    private String schema;
    private String tableName;
    private String tableComment;

    private DaoMo daoMo;
    private RepoMo repoMo;
    private RestMo restMo;

    private ColumnMo idColumn;
    private ColumnMo gmtCreate;
    private ColumnMo gmtModify;
    private List<ColumnMo> dataColumnList;
    private List<ColumnMo> columnList;
    private List<ColumnMo> insertList;

}
