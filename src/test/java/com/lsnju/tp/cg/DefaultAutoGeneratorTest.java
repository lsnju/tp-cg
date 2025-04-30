package com.lsnju.tp.cg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.lsnju.tp.cg.config.AutoGeneratorConfig;
import com.lsnju.tp.cg.config.GenConfigProperties;
import com.lsnju.tp.cg.config.TableConfigProperties;
import com.lsnju.tp.cg.config.enums.RestReturnTypeEnum;
import com.lsnju.tp.cg.config.enums.SpringDocVersionEnum;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ls
 * @since 2025/4/30 21:19
 * @version V1.0
 */
@Slf4j
public class DefaultAutoGeneratorTest {


    @Test
    void test_gen_all() {
        //
        gen("task", new String[]{"t_notify_task"}, true, true);
        gen("test", new String[]{"t_test"}, false, false);

    }


    private static void gen(String moduleName, String[] tableNames, boolean core, boolean rest) {
        try {
            final GenConfigProperties genConfig = new GenConfigProperties();
            genConfig.setNotNull(true);
            genConfig.setGenRepo(core);
            genConfig.setGenRest(rest);
            genConfig.setModuleName(moduleName);
            genConfig.setSpringDocVersion(SpringDocVersionEnum.JDK17);
            genConfig.setRestReturnType(RestReturnTypeEnum.SIMPLE);

            genConfig.setCreateTime("2025-05-20");
            genConfig.setBasePath("/home/ls/test-project");
            genConfig.setBasePackage("com.lsnju.test");
            genConfig.setDalPackage("common.dao");
            genConfig.setRepoPackage("core.model");
            genConfig.setDalModelSuffix("Do");
            genConfig.setMapperPath("/src/main/resources/mappers/");
            genConfig.setRestPackage("web.rest");

            final TableConfigProperties tableConfig = new TableConfigProperties();
            tableConfig.setIgnoreTablePrefix("tb_,t_");
            tableConfig.setIgnoreFieldPrefix("is_");
            tableConfig.setIdField("id,xx_id");
            tableConfig.setModifyField("update_time,update_date");
            tableConfig.setCreateField("create_time,create_date");
            final AutoGenerator generator = AutoGeneratorFactory.autoGenerator(AutoGeneratorConfig.builder()
                .connection(getJdbcConnection())
                .genConfig(genConfig)
                .tableConfig(tableConfig)
                .build());
            for (String tableName : tableNames) {
                log.info("{}", generator.gen(tableName));
            }
        } catch (Exception e) {
            log.error(String.format("%s", e.getMessage()), e);
        }
    }

    protected static Connection getJdbcConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/test_db";
        final String username = "root";
        return DriverManager.getConnection(url, username, null);
    }

}
