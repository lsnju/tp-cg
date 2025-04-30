package com.lsnju.tp.cg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lsnju.tp.cg.config.DbConfigProperties;
import com.lsnju.tp.cg.db.BaseTableDo;
import com.lsnju.tp.cg.model.res.DaoMo;
import com.lsnju.tp.cg.model.res.GenFileMo;
import com.lsnju.tp.cg.model.res.RepoMo;
import com.lsnju.tp.cg.model.res.RestMo;

import lombok.extern.slf4j.Slf4j;

/**
 * AutoGenerator for MyBatis Plus
 *
 * @author lisong
 * @since 2023/9/7 16:18
 * @version V1.0
 */
@Slf4j
public class MpAutoGenerator extends DefaultAutoGenerator {

    protected MpAutoGenerator(DbConfigProperties dbConfig) throws SQLException {
        super(dbConfig);
    }

    protected MpAutoGenerator(Connection connection) {
        super(connection);
    }

    @Override
    protected DaoMo initDaoMo(BaseTableDo table) throws SQLException {
        final DaoMo daoMo = new DaoMo();
        final String basePackage = genConfig.getBasePackage();
        final String dalPackage = genConfig.getDalPackage();
        final String domainName = getDomainName(table.getTableName());
        final String dalModelName = getDalModelName(domainName);

        daoMo.setModelName(dalModelName);
        daoMo.setApiName(getDalApiName(domainName));

        daoMo.setModelPackage(trimPackage(String.format("%s.%s.entity", basePackage, dalPackage)));
        daoMo.setApiPackage(trimPackage(String.format("%s.%s.mapper", basePackage, dalPackage)));

        daoMo.setModelPath(getDaoSavePath(String.format("%s.%s", daoMo.getModelPackage(), daoMo.getModelName())));
        daoMo.setApiPath(getDaoSavePath(String.format("%s.%s", daoMo.getApiPackage(), daoMo.getApiName())));

        // ...
        daoMo.setMapperPath(getMapperPathWithoutModuleName(domainName));

        final List<GenFileMo> list = new ArrayList<>();
        list.add(new GenFileMo(daoMo.getModelPath(), "mybatis-plus/dao/dao-model.ftl"));
        list.add(new GenFileMo(daoMo.getApiPath(), "mybatis-plus/dao/dao-api.ftl"));
        list.add(new GenFileMo(daoMo.getMapperPath(), String.format("mybatis-plus/mapper/%s.ftl", getDatabaseProductName())));
        daoMo.setGenFileList(list);
        return daoMo;
    }

    @Override
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
            list.add(new GenFileMo(repoMo.getModelPath(), "mybatis-plus/repo/repo-model.ftl"));
            list.add(new GenFileMo(repoMo.getPageReqPath(), "mybatis-plus/repo/repo-req-page.ftl"));
            list.add(new GenFileMo(repoMo.getQryReqPath(), "mybatis-plus/repo/repo-req-query.ftl"));
            list.add(new GenFileMo(repoMo.getApiPath(), "mybatis-plus/repo/repo-api.ftl"));
            list.add(new GenFileMo(repoMo.getImplPath(), "mybatis-plus/repo/repo-impl.ftl"));
            list.add(new GenFileMo(repoMo.getTestPath(), "mybatis-plus/test/fun-test.ftl"));
        }
        repoMo.setGenFileList(list);
        return repoMo;
    }

    @Override
    protected RestMo initRestMo(BaseTableDo table) {

        return new RestMo();
    }
}
