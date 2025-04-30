package com.lsnju.tp.cg.config;

import org.apache.commons.lang3.StringUtils;

import com.lsnju.base.model.BaseMo;
import com.lsnju.tp.cg.config.enums.DalOrgTypeEnum;
import com.lsnju.tp.cg.config.enums.RestReturnTypeEnum;
import com.lsnju.tp.cg.config.enums.SpringDocVersionEnum;
import com.lsnju.tp.cg.util.GenUtils;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/17 12:31
 * @version V1.0
 */
@Getter
@Setter
public class GenConfigProperties extends BaseMo {

    public static final String JAVA_PATH = "/src/main/java";
    public static final String MAPPER_PATH = "/src/main/resources/mybatis/mapper/";
    public static final String FUN_TEST_PATH = "/src/fun-test/java";
    public static final String WORKING_DIR = GenUtils.getWorkingDir();

    private boolean genRepo = true;
    private boolean genRest = true;

    private String sqlSessionName;

    private String domainPrefix;
    private String domainSuffix;

    private String dalModelPrefix;
    private String dalModelSuffix = "Do";
    private String dalApiSuffix = "Dao";
    private String repoModelPrefix;
    private String repoModelSuffix;
    private String restModelPrefix;
    private String restModelSuffix = "Vo";

    private String moduleName = "tp";
    private String basePath;
    private String daoBasePath;
    private String repoBasePath;
    private String restBasePath;

    private String javaPath = JAVA_PATH;
    private String mapperPath = MAPPER_PATH;
    private String funTestPath = FUN_TEST_PATH;

    private String basePackage = "com.lsnju.md";
    private String dalPackage = "common.dal";
    private String repoPackage = "core.model";
    private String restPackage = "web.rs";

    private String createAuthor;
    private String createTime;

    private SpringDocVersionEnum springDocVersion = SpringDocVersionEnum.JDK8;
    private RestReturnTypeEnum restReturnType = RestReturnTypeEnum.FULL;
    private DalOrgTypeEnum dalOrg = DalOrgTypeEnum.UNION;

    private boolean notNull;

    public void init() {
        this.domainPrefix = StringUtils.remove(this.domainPrefix, ".");
        this.domainSuffix = StringUtils.remove(this.domainSuffix, ".");
        this.repoModelPrefix = StringUtils.remove(this.repoModelPrefix, ".");
        this.repoModelSuffix = StringUtils.remove(this.repoModelSuffix, ".");
        this.moduleName = StringUtils.remove(this.moduleName, ".");
        this.basePath = path(this.basePath, WORKING_DIR);
        this.daoBasePath = path(this.daoBasePath);
        this.repoBasePath = path(this.repoBasePath);
        this.restBasePath = path(this.restBasePath);
        this.javaPath = path(this.javaPath, JAVA_PATH);
        this.mapperPath = path(this.mapperPath, MAPPER_PATH);
        this.funTestPath = path(this.funTestPath, FUN_TEST_PATH);
    }

    private String path(String path) {
        return path(path, path);
    }

    private String path(String path, String defaultPath) {
        if (StringUtils.isNotBlank(path)) {
            return StringUtils.removeEnd(StringUtils.replace(path, "\\", "/"), "/");
        }
        return defaultPath;
    }

    public String daoBasePath() {
        return StringUtils.defaultIfBlank(this.daoBasePath, this.basePath);
    }

    public String repoBasePath() {
        return StringUtils.defaultIfBlank(this.repoBasePath, this.basePath);
    }

    public String restBasePath() {
        return StringUtils.defaultIfBlank(this.restBasePath, this.basePath);
    }

}
