package com.lsnju.tp.cg;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import com.lsnju.base.model.BaseMo;
import com.lsnju.base.model.BaseRo;
import com.lsnju.tp.cg.config.DbConfigProperties;
import com.lsnju.tp.cg.config.GenConfigProperties;
import com.lsnju.tp.cg.config.TableConfigProperties;
import com.lsnju.tp.cg.config.enums.RestReturnTypeEnum;
import com.lsnju.tp.cg.config.enums.SpringDocVersionEnum;
import com.lsnju.tp.cg.db.BaseColumnDo;
import com.lsnju.tp.cg.db.BaseTableDo;
import com.lsnju.tp.cg.db.DbConnectionUtils;
import com.lsnju.tp.cg.model.ColumnMo;
import com.lsnju.tp.cg.model.DbMo;
import com.lsnju.tp.cg.model.TableMo;
import com.lsnju.tp.cg.model.TemplateMo;
import com.lsnju.tp.cg.model.enums.ColTypeEnum;
import com.lsnju.tp.cg.model.enums.DbTypeEnum;
import com.lsnju.tp.cg.model.res.DaoMo;
import com.lsnju.tp.cg.model.res.GenFileMo;
import com.lsnju.tp.cg.model.res.RepoMo;
import com.lsnju.tp.cg.model.res.RestMo;
import com.lsnju.tp.cg.util.GenUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ls
 * @since 2023/8/18 5:59
 * @version V1.0
 */
@Slf4j
public class DefaultAutoGenerator extends BaseMo implements AutoGenerator {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    protected final DbConfigProperties dbConfig;
    protected final Connection connection;
    protected final DbMo dbMo = new DbMo();
    @Setter
    protected GenConfigProperties genConfig = new GenConfigProperties();
    @Setter
    protected TableConfigProperties tableConfig = new TableConfigProperties();

    protected Set<String> idSet;
    protected Set<String> createSet;
    protected Set<String> modifySet;
    protected Set<String> ignoreTablePrefixSet;
    protected Set<String> ignoreTableSuffixSet;
    protected Set<String> ignoreFieldPrefixSet;
    protected Set<String> ignoreFieldSuffixSet;

    protected DefaultAutoGenerator(DbConfigProperties dbConfig) throws SQLException {
        this.dbConfig = dbConfig;
        this.connection = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    protected DefaultAutoGenerator(Connection connection) {
        this.dbConfig = DbConfigProperties.builder().build();
        this.connection = connection;
    }

    @Override
    public void init() throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        this.dbMo.setDbTypeEnum(DbTypeEnum.getByCode(metaData.getDatabaseProductName()));
        this.dbMo.setDriverName(metaData.getDriverName());
        this.dbMo.setDriverVersion(metaData.getDriverVersion());
        this.dbMo.setProductName(metaData.getDatabaseProductName());
        this.dbMo.setProductVersion(metaData.getDatabaseProductVersion());
        this.dbMo.setMajorVersion(metaData.getDatabaseMajorVersion());
        this.dbMo.setMinorVersion(metaData.getDatabaseMinorVersion());
        this.dbMo.setSupportNoWait(isSupportNoWait(this.dbMo));
        this.dbMo.setNotNull(genConfig.isNotNull());
        String[] keywords = StringUtils.split(metaData.getSQLKeywords(), ",");
        for (String keyword : keywords) {
            this.dbMo.addKeyword(keyword);
        }
        this.genConfig.init();
        this.idSet = tableConfig.idSet();
        this.createSet = tableConfig.createSet();
        this.modifySet = tableConfig.modifySet();
        this.ignoreTablePrefixSet = tableConfig.ignoreTablePrefixSet();
        this.ignoreTableSuffixSet = tableConfig.ignoreTableSuffixSet();
        this.ignoreFieldPrefixSet = tableConfig.ignoreFieldPrefixSet();
        this.ignoreFieldSuffixSet = tableConfig.ignoreFieldSuffixSet();
        this.cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/templates"));
        this.cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        this.cfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        this.cfg.setNumberFormat("0.##########");
        this.cfg.setClassicCompatible(true);
        final BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_32).build();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        try {
            this.cfg.setSharedVariable("String", staticModels.get("java.lang.String"));
        } catch (TemplateModelException e) {
            log.error(String.format("%s", e.getMessage()), e);
        }
        if (log.isDebugEnabled()) {
            log.debug("{}", this.dbConfig);
            log.debug("{}", this.tableConfig);
            log.debug("{}", this.genConfig);
            log.debug("{}", this.dbMo);
        }
    }

    // PostgreSQL
    // MariaDB
    @Override
    public String getDatabaseProductName() throws SQLException {
        return this.connection.getMetaData().getDatabaseProductName();
    }

    private boolean isSupportNoWait(DbMo dbMo) {
        if (dbMo.getDbTypeEnum() != null) {
            switch (dbMo.getDbTypeEnum()) {
                case PostgreSQL:
                    return true;
                case MySQL:
                    return dbMo.getMajorVersion() > 5;
                default:
            }
        }
        return true;
    }

    @Override
    public List<BaseTableDo> tableList() throws SQLException {
        Objects.requireNonNull(connection);
        return DbConnectionUtils.tableList(connection, null);
    }

    @Override
    public BaseTableDo table(String tableName) throws SQLException {
        Objects.requireNonNull(connection);
        final List<BaseTableDo> list = DbConnectionUtils.tableList(connection, tableName);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<BaseColumnDo> columnList(String tableName) throws SQLException {
        Objects.requireNonNull(connection);
        return DbConnectionUtils.columnList(connection, tableName);
    }

    @Override
    public BaseRo gen(String tableName) throws SQLException {
        final BaseTableDo table = table(tableName);
        log.info("tableInfo = {}", table);
        Objects.requireNonNull(table);
        final List<BaseColumnDo> list = columnList(tableName);

        final List<ColumnMo> dataColumnList = new ArrayList<>();
        final List<ColumnMo> columnList = new ArrayList<>();
        final List<ColumnMo> insertList = new ArrayList<>();
        final TableMo tableMo = new TableMo();
        tableMo.setDbMo(this.dbMo);
        tableMo.setSchema(table.getSchema());
        tableMo.setTableName(table.getTableName());
        tableMo.setTableComment(table.getTableComment());
        tableMo.setDataColumnList(dataColumnList);
        tableMo.setColumnList(columnList);
        tableMo.setInsertList(insertList);

        for (BaseColumnDo item : list) {
            if (idSet.contains(item.getColumnName()) && tableMo.getIdColumn() == null) {
                final ColumnMo col = convert(item);
                tableMo.setIdColumn(col);
                columnList.add(col);
                if (!col.isAutoIncrement()) {
                    insertList.add(col);
                }
            } else if (createSet.contains(item.getColumnName()) && tableMo.getGmtCreate() == null) {
                final ColumnMo col = convert(item);
                col.setNow(true);
                tableMo.setGmtCreate(col);
                columnList.add(col);
                insertList.add(col);
            } else if (modifySet.contains(item.getColumnName()) && tableMo.getGmtModify() == null) {
                final ColumnMo col = convert(item);
                col.setNow(true);
                tableMo.setGmtModify(col);
                columnList.add(col);
                insertList.add(col);
            } else {
                final ColumnMo col = convert(item);
                columnList.add(col);
                dataColumnList.add(col);
                insertList.add(col);
            }
        }
        log.info("columnList = {}", tableMo.getColumnList());
        log.info("getIdColumn = {}", tableMo.getIdColumn());
        tableMo.setDaoMo(initDaoMo(table));
        tableMo.setRepoMo(initRepoMo(table));
        tableMo.setRestMo(initRestMo(table));
        log.info("daoMo = {}", tableMo.getDaoMo());
        log.info("repoMo = {}", tableMo.getRepoMo());
        log.info("restMo = {}", tableMo.getRestMo());

        final TemplateMo templateMo = new TemplateMo();
        templateMo.setAuthor(genConfig.getCreateAuthor());
        templateMo.setTime(genConfig.getCreateTime());

        final Map<String, Object> map = getMap();
        map.put("tableMo", tableMo);
        map.put("templateMo", templateMo);
        map.put("genConfig", this.genConfig);
        genFile(tableMo.getDaoMo().getGenFileList(), map);
        genFile(tableMo.getRepoMo().getGenFileList(), map);
        genFile(tableMo.getRestMo().getGenFileList(), map);

        return BaseRo.SUCCESS;
    }

    private void genFile(List<GenFileMo> tableMo, Map<String, Object> map) {
        if (tableMo == null || tableMo.isEmpty()) {
            return;
        }
        for (GenFileMo item : tableMo) {
            log.info("{}", item.getTemplate());
            log.info("{}", item.getPath());
            mkdirsIfNotExists(item.getPath());
            try (Writer writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(item.getPath())), UTF_8)) {
                cfg.getTemplate(item.getTemplate()).process(map, writer);
            } catch (Exception e) {
                log.error(String.format("item=%s, %s", item, e.getMessage()), e);
            }
        }
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("sysProps", System.getProperties());
        return map;
    }

    protected DaoMo initDaoMo(BaseTableDo table) throws SQLException {
        DaoMo daoMo = new DaoMo();
        final String basePackage = genConfig.getBasePackage();
        final String dalPackage = genConfig.getDalPackage();
        final String moduleName = StringUtils.defaultString(genConfig.getModuleName());
        final String domainName = getDomainName(table.getTableName());
        final String dalModelName = getDalModelName(domainName);

        daoMo.setModelName(dalModelName);
        daoMo.setApiName(getDalApiName(domainName));
        daoMo.setQryReqName(daoMo.getModelName() + "QryReq");
        daoMo.setPageReqName(daoMo.getModelName() + "PageQryReq");
        daoMo.setImplName(daoMo.getApiName() + "Impl");

        Objects.requireNonNull(genConfig.getDalOrg());
        switch (genConfig.getDalOrg()) {
            case UNION:
                daoMo.setModelPackage(trimPackage(String.format("%s.%s.%s.dbo", basePackage, dalPackage, moduleName)));
                daoMo.setApiPackage(trimPackage(String.format("%s.%s.%s", basePackage, dalPackage, moduleName)));
                daoMo.setImplPackage(trimPackage(String.format("%s.%s.%s.impl", basePackage, dalPackage, moduleName)));
                daoMo.setPageReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, dalPackage, moduleName)));
                daoMo.setQryReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, dalPackage, moduleName)));
                break;
            case SEPARATE:
                daoMo.setModelPackage(trimPackage(String.format("%s.%s.dbo.%s", basePackage, dalPackage, moduleName)));
                daoMo.setApiPackage(trimPackage(String.format("%s.%s.dao.%s", basePackage, dalPackage, moduleName)));
                daoMo.setImplPackage(trimPackage(String.format("%s.%s.impl.%s", basePackage, dalPackage, moduleName)));
                daoMo.setPageReqPackage(trimPackage(String.format("%s.%s.dao.%s.req", basePackage, dalPackage, moduleName)));
                daoMo.setQryReqPackage(trimPackage(String.format("%s.%s.dao.%s.req", basePackage, dalPackage, moduleName)));
                break;
            default:
                throw new RuntimeException("Not Support " + genConfig.getDalOrg());
        }

        daoMo.setModelPath(getDaoSavePath(String.format("%s.%s", daoMo.getModelPackage(), daoMo.getModelName())));
        daoMo.setApiPath(getDaoSavePath(String.format("%s.%s", daoMo.getApiPackage(), daoMo.getApiName())));
        daoMo.setQryReqPath(getDaoSavePath(String.format("%s.%s", daoMo.getQryReqPackage(), daoMo.getQryReqName())));
        daoMo.setPageReqPath(getDaoSavePath(String.format("%s.%s", daoMo.getPageReqPackage(), daoMo.getPageReqName())));
        daoMo.setImplPath(getDaoSavePath(String.format("%s.%s", daoMo.getImplPackage(), daoMo.getImplName())));
        // ...
        daoMo.setMapperPath(getMapperPath(domainName));
        daoMo.setSqlSessionName(this.genConfig.getSqlSessionName());

        final List<GenFileMo> list = new ArrayList<>();
        list.add(new GenFileMo(daoMo.getModelPath(), "default/dao/dao-model.ftl"));
        list.add(new GenFileMo(daoMo.getApiPath(), "default/dao/dao-api.ftl"));
        list.add(new GenFileMo(daoMo.getImplPath(), "default/dao/dao-impl.ftl"));
        list.add(new GenFileMo(daoMo.getPageReqPath(), "default/dao/dao-req-page.ftl"));
        list.add(new GenFileMo(daoMo.getQryReqPath(), "default/dao/dao-req-query.ftl"));
        list.add(new GenFileMo(daoMo.getMapperPath(), String.format("default/mapper/%s.ftl", getDatabaseProductName())));
        daoMo.setGenFileList(list);
        return daoMo;
    }

    protected RepoMo initRepoMo(BaseTableDo table) {
        RepoMo repoMo = new RepoMo();
        final String basePackage = genConfig.getBasePackage();
        final String repoPackage = genConfig.getRepoPackage();
        final String moduleName = StringUtils.defaultString(genConfig.getModuleName());
        final String domainName = getDomainName(table.getTableName());
        final String repoModelName = getRepoModelName(domainName);

        repoMo.setModelName(repoModelName);
        repoMo.setApiName(domainName + "Repository");
        repoMo.setPageReqName(domainName + "PageQryReq");
        repoMo.setQryReqName(domainName + "QryReq");
        repoMo.setImplName(repoMo.getApiName() + "Impl");
        repoMo.setTestName(repoMo.getApiName() + "FunTest");

        repoMo.setModelPackage(trimPackage(String.format("%s.%s.%s.model", basePackage, repoPackage, moduleName)));
        repoMo.setApiPackage(trimPackage(String.format("%s.%s.%s", basePackage, repoPackage, moduleName)));
        repoMo.setPageReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, repoPackage, moduleName)));
        repoMo.setQryReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, repoPackage, moduleName)));
        repoMo.setImplPackage(trimPackage(String.format("%s.%s.%s.impl", basePackage, repoPackage, moduleName)));
        repoMo.setTestPackage(repoMo.getApiPackage());

        repoMo.setModelPath(getRepoSavePath(String.format("%s.%s", repoMo.getModelPackage(), repoMo.getModelName())));
        repoMo.setApiPath(getRepoSavePath(String.format("%s.%s", repoMo.getApiPackage(), repoMo.getApiName())));
        repoMo.setPageReqPath(getRepoSavePath(String.format("%s.%s", repoMo.getPageReqPackage(), repoMo.getPageReqName())));
        repoMo.setQryReqPath(getRepoSavePath(String.format("%s.%s", repoMo.getQryReqPackage(), repoMo.getQryReqName())));
        repoMo.setImplPath(getRepoSavePath(String.format("%s.%s", repoMo.getImplPackage(), repoMo.getImplName())));
        repoMo.setTestPath(getRepoTestPath(String.format("%s.%s", repoMo.getTestPackage(), repoMo.getTestName())));

        final List<GenFileMo> list = new ArrayList<>();
        if (genConfig.isGenRepo()) {
            list.add(new GenFileMo(repoMo.getModelPath(), "default/repo/repo-model.ftl"));
            list.add(new GenFileMo(repoMo.getApiPath(), "default/repo/repo-api.ftl"));
            list.add(new GenFileMo(repoMo.getImplPath(), "default/repo/repo-impl.ftl"));
            list.add(new GenFileMo(repoMo.getPageReqPath(), "default/repo/repo-req-page.ftl"));
            list.add(new GenFileMo(repoMo.getQryReqPath(), "default/repo/repo-req-query.ftl"));
            list.add(new GenFileMo(repoMo.getTestPath(), "default/test/fun-test.ftl"));
        }
        repoMo.setGenFileList(list);
        return repoMo;
    }

    protected RestMo initRestMo(BaseTableDo table) {
        RestMo restMo = new RestMo();
        final String basePackage = genConfig.getBasePackage();
        final String restPackage = genConfig.getRestPackage();
        final String moduleName = StringUtils.defaultString(genConfig.getModuleName());
        final String domainName = getDomainName(table.getTableName());
        restMo.setBasePackage(String.format("%s.%s", basePackage, restPackage));
        final String restModelName = getRestModelName(domainName);

        restMo.setModelName(restModelName);
        restMo.setApiName(domainName + "Rest");
        restMo.setImplName(domainName + "RestImpl");
        restMo.setPageReqName(domainName + "PageRequest");
        restMo.setAddReqName(domainName + "AddRequest");

        restMo.setModelPackage(trimPackage(String.format("%s.%s.%s.vo", basePackage, restPackage, moduleName)));
        restMo.setApiPackage(trimPackage(String.format("%s.%s.%s", basePackage, restPackage, moduleName)));
        restMo.setImplPackage(trimPackage(String.format("%s.%s.%s.impl", basePackage, restPackage, moduleName)));
        restMo.setPageReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, restPackage, moduleName)));
        restMo.setAddReqPackage(trimPackage(String.format("%s.%s.%s.req", basePackage, restPackage, moduleName)));

        restMo.setModelPath(getRestSavePath(String.format("%s.%s", restMo.getModelPackage(), restMo.getModelName())));
        restMo.setApiPath(getRestSavePath(String.format("%s.%s", restMo.getApiPackage(), restMo.getApiName())));
        restMo.setImplPath(getRestSavePath(String.format("%s.%s", restMo.getImplPackage(), restMo.getImplName())));
        restMo.setPageReqPath(getRestSavePath(String.format("%s.%s", restMo.getPageReqPackage(), restMo.getPageReqName())));
        restMo.setAddReqPath(getRestSavePath(String.format("%s.%s", restMo.getAddReqPackage(), restMo.getAddReqName())));
        restMo.setApiConstantPath(getRestSavePath(String.format("%s.ApiConstants", restMo.getBasePackage())));

        final List<GenFileMo> list = new ArrayList<>();
        if (genConfig.isGenRest()) {
            list.add(new GenFileMo(restMo.getModelPath(), "default/rest/rest-model.ftl"));
            list.add(new GenFileMo(restMo.getPageReqPath(), "default/rest/rest-req-page.ftl"));
            list.add(new GenFileMo(restMo.getAddReqPath(), "default/rest/rest-req-add.ftl"));
            if (genConfig.getSpringDocVersion() == SpringDocVersionEnum.JDK17) {
                if (genConfig.getRestReturnType() == RestReturnTypeEnum.SIMPLE) {
                    list.add(new GenFileMo(restMo.getApiPath(), "default/rest/rest-api-sd-v2-simple.ftl"));
                    list.add(new GenFileMo(restMo.getImplPath(), "default/rest/rest-impl-sd-v2-simple.ftl"));
                } else {
                    list.add(new GenFileMo(restMo.getApiPath(), "default/rest/rest-api-sd-v2.ftl"));
                    list.add(new GenFileMo(restMo.getImplPath(), "default/rest/rest-impl-sd-v2.ftl"));
                }
            } else {
                if (genConfig.getRestReturnType() == RestReturnTypeEnum.SIMPLE) {
                    list.add(new GenFileMo(restMo.getApiPath(), "default/rest/rest-api-sd-v1-simple.ftl"));
                    list.add(new GenFileMo(restMo.getImplPath(), "default/rest/rest-impl-sd-v1-simple.ftl"));
                } else {
                    list.add(new GenFileMo(restMo.getApiPath(), "default/rest/rest-api-sd-v1.ftl"));
                    list.add(new GenFileMo(restMo.getImplPath(), "default/rest/rest-impl-sd-v1.ftl"));
                }
            }
            if (!new File(restMo.getApiConstantPath()).exists()) {
                log.info("gen_api_constants");
                list.add(new GenFileMo(restMo.getApiConstantPath(), "default/rest/rest-api-constants.ftl"));
            }
        }
        restMo.setGenFileList(list);

        return restMo;
    }


    protected String trimPackage(String packageName) {
        return Strings.CS.removeEnd(Strings.CS.replace(packageName, "..", "."), ".");
    }

    protected String getRepoTestPath(String fullClassName) {
        final String javaFilePath = Strings.CS.replace(fullClassName, ".", "/");
        return String.format("%s%s/%s.java", trimPath(genConfig.repoBasePath()), trimPath(genConfig.getFunTestPath()), javaFilePath);
    }

    protected String getSavePath(String fullClassName) {
        final String javaFilePath = Strings.CS.replace(fullClassName, ".", "/");
        return String.format("%s%s/%s.java", trimPath(genConfig.getBasePath()), trimPath(genConfig.getJavaPath()), javaFilePath);
    }

    protected String getDaoSavePath(String fullClassName) {
        final String javaFilePath = Strings.CS.replace(fullClassName, ".", "/");
        return String.format("%s%s/%s.java", trimPath(genConfig.daoBasePath()), trimPath(genConfig.getJavaPath()), javaFilePath);
    }

    protected String getRepoSavePath(String fullClassName) {
        final String javaFilePath = Strings.CS.replace(fullClassName, ".", "/");
        return String.format("%s%s/%s.java", trimPath(genConfig.repoBasePath()), trimPath(genConfig.getJavaPath()), javaFilePath);
    }

    protected String getRestSavePath(String fullClassName) {
        final String javaFilePath = Strings.CS.replace(fullClassName, ".", "/");
        return String.format("%s%s/%s.java", trimPath(genConfig.restBasePath()), trimPath(genConfig.getJavaPath()), javaFilePath);
    }

    protected String getMapperPath(String domainName) {
        if (StringUtils.isNotBlank(genConfig.getModuleName())) {
            return String.format("%s%s/%s/%sMapper.xml", trimPath(genConfig.daoBasePath()),
                trimPath(genConfig.getMapperPath()),
                trimPath(genConfig.getModuleName()), domainName);
        }
        return getMapperPathWithoutModuleName(domainName);
    }

    protected String getMapperPathWithoutModuleName(String domainName) {
        return String.format("%s%s/%sMapper.xml", trimPath(genConfig.daoBasePath()),
            trimPath(genConfig.getMapperPath()), domainName);
    }

    private void mkdirsIfNotExists(String savePath) {
        File f = new File(savePath);
        File dir = f.getParentFile();
        if (!dir.exists() && dir.mkdirs()) {
            log.info("mkdirs");
        }
    }

    private String trimPath(String path) {
        return Strings.CS.removeEnd(path, "/");
    }

    private ColumnMo convert(BaseColumnDo item) {
        final ColTypeEnum colTypeEnum = GenUtils.getJavaType(item.getDataType());
        final ColumnMo column = new ColumnMo();
        column.setAutoIncrement(item.isAutoIncrement());
        column.setOrderNo(item.getOrderNo());
        column.setColumnName(item.getColumnName());
        column.setColumnType(item.getColumnType());
        column.setColumnComment(item.getColumnComment());
        column.setDataType(item.getDataType());
        column.setNullable(item.getNullable());
        column.setDefaultValue(item.getDefaultValue());
        column.setMaxLength(item.getMaxLength());
        column.setPropName(getPropName(item.getColumnName()));
        column.setPropType(colTypeEnum);
        column.setPropGetMethod(getPropGetMethod(item.getColumnName(), colTypeEnum));
        column.setPropSetMethod(getPropSetMethod(item.getColumnName()));
        return column;
    }

    private String getColumnName(String columnName) {
        String ret = columnName;
        for (String item : ignoreFieldPrefixSet) {
            ret = Strings.CI.removeStart(ret, item);
        }
        for (String item : ignoreFieldSuffixSet) {
            ret = Strings.CI.removeEnd(ret, item);
        }
        return ret;
    }

    private String getPropGetMethod(String columnName, ColTypeEnum colTypeEnum) {
        if (colTypeEnum == ColTypeEnum.BOOL) {
            return "is" + StringUtils.capitalize(getPropName(columnName));
        }
        return "get" + StringUtils.capitalize(getPropName(columnName));
    }

    private String getPropSetMethod(String columnName) {
        return "set" + StringUtils.capitalize(getPropName(columnName));
    }

    private String getPropName(String columnName) {
        String propName = GenUtils.getLowCaseName(getColumnName(columnName));
        if (this.dbMo.containsKeyword(StringUtils.upperCase(propName))) {
            return propName + "Tag";
        }
        return propName;
    }

    protected String getDomainName(final String tableName) {
        String name = tableName;
        for (String item : ignoreTablePrefixSet) {
            name = Strings.CI.removeStart(name, item);
        }
        for (String item : ignoreTablePrefixSet) {
            name = Strings.CI.removeEnd(name, item);
        }
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(genConfig.getDomainPrefix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getDomainPrefix()));
        }
        sb.append(GenUtils.getUpCaseName(name));
        if (StringUtils.isNotBlank(genConfig.getDomainSuffix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getDomainSuffix()));
        }
        return sb.toString();
    }

    protected String getRestModelName(String domainName) {
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(genConfig.getRestModelPrefix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getRestModelPrefix()));
        }
        sb.append(domainName);
        if (StringUtils.isNotBlank(genConfig.getRestModelSuffix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getRestModelSuffix()));
        }
        return sb.toString();
    }

    protected String getRepoModelName(String domainName) {
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(genConfig.getRepoModelPrefix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getRepoModelPrefix()));
        }
        sb.append(domainName);
        if (StringUtils.isNotBlank(genConfig.getRepoModelSuffix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getRepoModelSuffix()));
        }
        return sb.toString();
    }

    protected String getDalModelName(String domainName) {
        final StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(genConfig.getDalModelPrefix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getDalModelPrefix()));
        }
        sb.append(domainName);
        if (StringUtils.isNotBlank(genConfig.getDalModelSuffix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getDalModelSuffix()));
        }
        return sb.toString();
    }

    protected String getDalApiName(String domainName) {
        final StringBuilder sb = new StringBuilder();
        sb.append(domainName);
        if (StringUtils.isNotBlank(genConfig.getDalApiSuffix())) {
            sb.append(GenUtils.getUpCaseName(genConfig.getDalApiSuffix()));
        } else {
            sb.append("Dao");
        }
        return sb.toString();
    }

}
