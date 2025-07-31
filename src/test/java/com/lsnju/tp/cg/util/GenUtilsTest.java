package com.lsnju.tp.cg.util;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ls
 * @since 2025/7/31 11:18
 * @version V1.0
 */
@Slf4j
public class GenUtilsTest {

    @Test
    void test_001() {
        log.info("{}", GenUtils.getWorkingDir());
        log.info("{}", GenUtils.getWorkingDir());
    }

}
