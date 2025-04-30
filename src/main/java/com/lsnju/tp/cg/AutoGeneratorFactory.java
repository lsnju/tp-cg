package com.lsnju.tp.cg;

import java.sql.SQLException;
import java.util.Objects;

import com.lsnju.tp.cg.config.AutoGeneratorConfig;
import com.lsnju.tp.cg.config.GenConfigProperties;
import com.lsnju.tp.cg.config.TableConfigProperties;

/**
 *
 * @author ls
 * @since 2023/9/7 20:39
 * @version V1.0
 */
public class AutoGeneratorFactory {

    public static AutoGenerator autoGenerator(AutoGeneratorConfig config) throws SQLException {
        DefaultAutoGenerator generator;
        if (config.getConnection() != null) {
            generator = new DefaultAutoGenerator(config.getConnection());
        } else {
            Objects.requireNonNull(config.getDbConfig());
            generator = new DefaultAutoGenerator(config.getDbConfig());
        }
        Objects.requireNonNull(generator);
        generator.setTableConfig(config.getTableConfig() != null ? config.getTableConfig() : new TableConfigProperties());
        generator.setGenConfig(config.getGenConfig() != null ? config.getGenConfig() : new GenConfigProperties());
        generator.init();
        return generator;
    }

    public static AutoGenerator mpAutoGenerator(AutoGeneratorConfig config) throws SQLException {
        MpAutoGenerator generator;
        if (config.getConnection() != null) {
            generator = new MpAutoGenerator(config.getConnection());
        } else {
            Objects.requireNonNull(config.getDbConfig());
            generator = new MpAutoGenerator(config.getDbConfig());
        }
        Objects.requireNonNull(generator);
        generator.setTableConfig(config.getTableConfig() != null ? config.getTableConfig() : new TableConfigProperties());
        generator.setGenConfig(config.getGenConfig() != null ? config.getGenConfig() : new GenConfigProperties());
        generator.init();
        return generator;
    }

}
