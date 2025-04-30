package com.lsnju.tp.cg.config.mp;

import com.lsnju.tp.cg.config.GenConfigProperties;

/**
 *
 * @author ls
 * @since 2023/9/9 9:40
 * @version V1.0
 */
public class MpGenConfigProperties extends GenConfigProperties {
    {
        this.setGenRest(false);
        this.setModuleName("");
        this.setDalPackage("domain");
        this.setDalModelSuffix("Entity");
        this.setDalApiSuffix("Mapper");
        this.setRepoPackage("repository");
        this.setCreateTime("2023-09-09 09:27:16");
        this.setMapperPath("/src/main/resources/mapper/");
    }
}
