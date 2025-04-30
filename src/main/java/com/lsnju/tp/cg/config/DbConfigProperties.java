package com.lsnju.tp.cg.config;

import com.lsnju.base.model.BaseMo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/16 22:14
 * @version V1.0
 */
@Getter
@Setter
@Builder
public class DbConfigProperties extends BaseMo {

    private String type;

    private String jdbcUrl;
    private String username;
    private String password;

}
