<#ftl strip_whitespace=true>
package ${tableMo.daoMo.implPackage};

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
<#if tableMo.daoMo.sqlSessionName??>
import org.springframework.beans.factory.annotation.Qualifier;
</#if>
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import ${tableMo.daoMo.apiPackage}.${tableMo.daoMo.apiName};
import ${tableMo.daoMo.pageReqPackage}.${tableMo.daoMo.pageReqName};
import ${tableMo.daoMo.qryReqPackage}.${tableMo.daoMo.qryReqName};
import ${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName};
import com.lsnju.base.model.PageList;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * ${tableMo.daoMo.implName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Slf4j
@Setter
@Repository
public class ${tableMo.daoMo.implName} implements ${tableMo.daoMo.apiName} {

    /** NAMESPACE */
    private static final String NAMESPACE = ${tableMo.daoMo.apiName}.class.getName();

    @Autowired
    <#if tableMo.daoMo.sqlSessionName??>
    @Qualifier("${tableMo.daoMo.sqlSessionName}")
    </#if>
    private SqlSession sqlSession;

    @Override
    public ${tableMo.idColumn.propType.type} add(${tableMo.daoMo.modelName} a${tableMo.daoMo.modelName}) {
        int size = sqlSession.insert(NAMESPACE + ".add", a${tableMo.daoMo.modelName});
        log.debug("insert size = {}", size);
        Assert.isTrue(size == 1, "insert a${tableMo.daoMo.modelName} error");
        return a${tableMo.daoMo.modelName}.${tableMo.idColumn.propGetMethod}();
    }

    @Override
    public ${tableMo.daoMo.modelName} getById(${tableMo.idColumn.propType.type} id) {
        return sqlSession.selectOne(NAMESPACE + ".getById", id);
    }

    @Override
    public ${tableMo.daoMo.modelName} lockById(${tableMo.idColumn.propType.type} id) {
        return sqlSession.selectOne(NAMESPACE + ".lockById", id);
    }

    @Override
    public ${tableMo.daoMo.modelName} lockByIdWait(${tableMo.idColumn.propType.type} id) {
        return sqlSession.selectOne(NAMESPACE + ".lockByIdWait", id);
    }

    @Override
    public ${tableMo.daoMo.modelName} lockByIdNoWait(${tableMo.idColumn.propType.type} id) {
        return sqlSession.selectOne(NAMESPACE + ".lockByIdNoWait", id);
    }

    @Override
    public int update(${tableMo.daoMo.modelName} a${tableMo.daoMo.modelName}) {
        return sqlSession.update(NAMESPACE + ".update", a${tableMo.daoMo.modelName});
    }

    @Override
    public int delete(${tableMo.idColumn.propType.type} id) {
        return sqlSession.delete(NAMESPACE + ".delete", id);
    }

    @Override
    public PageList<${tableMo.daoMo.modelName}> pageQuery(${tableMo.daoMo.pageReqName} req) {
        log.info("{}", req);
        PageList<${tableMo.daoMo.modelName}> result = new PageList<>();
        result.setPageSize(req.getPageSize());
        result.setPageNum(req.getPageNum());

        Map<String, Object> params = new HashMap<>();
        // params.put("systemName", systemName);

        long count = sqlSession.selectOne(NAMESPACE + ".pageQueryCount", params);
        long maxPageNum = Math.round(Math.ceil(count * 1.0 / req.getPageSize()));

        result.setTotalCount(count);
        result.setMaxPageNum(maxPageNum);
        if (req.getPageNum() > maxPageNum) {
            result.setResultList(Collections.emptyList());
            return result;
        }

        params.put("limit", req.limit());
        params.put("offset", req.offset());
        result.setResultList(sqlSession.selectList(NAMESPACE + ".pageQuery", params));
        return result;
    }

    @Override
    public List<${tableMo.daoMo.modelName}> getByCond(${tableMo.daoMo.qryReqName} req) {
        Map<String, Object> params = new HashMap<>();
        // params.put("systemName", systemName);
        return sqlSession.selectList(NAMESPACE + ".getByCond", params);
    }

}
