package com.lsnju.tp.cg.config;

import java.sql.Connection;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author ls
 * @since 2023/9/7 20:26
 * @version V1.0
 */
@Builder
@Getter
public class AutoGeneratorConfig {
    protected final DbConfigProperties dbConfig;
    protected final Connection connection;
    protected final GenConfigProperties genConfig;
    protected final TableConfigProperties tableConfig;
}
