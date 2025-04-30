package com.lsnju.tp.cg;

import java.sql.SQLException;
import java.util.List;

import com.lsnju.base.model.BaseRo;
import com.lsnju.tp.cg.db.BaseColumnDo;
import com.lsnju.tp.cg.db.BaseTableDo;

/**
 *
 * @author ls
 * @since 2023/8/19 10:28
 * @version V1.0
 */
public interface AutoGenerator {

    void init() throws SQLException;

    String getDatabaseProductName() throws SQLException;

    List<BaseTableDo> tableList() throws SQLException;

    BaseTableDo table(String tableName) throws SQLException;

    List<BaseColumnDo> columnList(String tableName) throws SQLException;

    BaseRo gen(String tableName) throws SQLException;

}
