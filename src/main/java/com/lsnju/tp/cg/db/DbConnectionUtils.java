package com.lsnju.tp.cg.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Strings;

/**
 *
 * @author ls
 * @since 2023/8/18 6:53
 * @version V1.0
 */
public class DbConnectionUtils {

    public static final String[] TABLE_TYPES = {"TABLE"};

    public static List<BaseTableDo> tableList(Connection connection, String tableName) throws SQLException {
        final List<BaseTableDo> list = new ArrayList<>();
        final String catalog = connection.getCatalog();
        final String schema = connection.getSchema();
        final DatabaseMetaData metaData = connection.getMetaData();
        final ResultSet tables = metaData.getTables(catalog, schema, tableName, TABLE_TYPES);

        while (tables.next()) {
            final BaseTableDo item = BaseTableDo.builder()
                .catalog(catalog)
                .schema(schema)
                .tableName(tables.getString("TABLE_NAME"))
                .tableType(tables.getString("TABLE_TYPE"))
                .tableComment(tables.getString("REMARKS"))
                .build();
            list.add(item);
        }
        return list;
    }

    public static List<BaseColumnDo> columnList(Connection connection, String tableName) throws SQLException {
        final List<BaseColumnDo> ret = new ArrayList<>();
        final String catalog = connection.getCatalog();
        final String schema = connection.getSchema();
        final DatabaseMetaData metaData = connection.getMetaData();
        final ResultSet column = metaData.getColumns(catalog, schema, tableName, null);

        while (column.next()) {
            final int dataType = column.getInt("DATA_TYPE");
            final BaseColumnDo item = BaseColumnDo.builder()
                .catalog(catalog)
                .schema(schema)
                .tableName(column.getString("TABLE_NAME"))
                .orderNo(column.getInt("ORDINAL_POSITION"))
                .columnName(column.getString("COLUMN_NAME"))
                .columnType(column.getString("TYPE_NAME"))
                .columnComment(column.getString("REMARKS"))
                .dataType(JDBCType.valueOf(dataType))
                .nullable(column.getString("IS_NULLABLE"))
                .defaultValue(column.getString("COLUMN_DEF"))
                .maxLength(column.getInt("COLUMN_SIZE"))
                .autoIncrement(Strings.CI.equals("YES", column.getString("IS_AUTOINCREMENT")))
//                .columnKey(column.getString(""))
//                .precision(column.getInt(""))
//                .scale(column.getInt(""))
                .build();
            ret.add(item);
        }
        return ret;
    }

}
