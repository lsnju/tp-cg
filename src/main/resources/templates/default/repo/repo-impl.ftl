package ${tableMo.repoMo.implPackage};

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import ${tableMo.daoMo.apiPackage}.${tableMo.daoMo.apiName};
import ${tableMo.daoMo.pageReqPackage}.${tableMo.daoMo.pageReqName};
import ${tableMo.daoMo.qryReqPackage}.${tableMo.daoMo.qryReqName};
import ${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName};
import ${tableMo.repoMo.apiPackage}.${tableMo.repoMo.apiName};
import ${tableMo.repoMo.modelPackage}.${tableMo.repoMo.modelName};
import ${tableMo.repoMo.pageReqPackage}.${tableMo.repoMo.pageReqName};
import ${tableMo.repoMo.qryReqPackage}.${tableMo.repoMo.qryReqName};
import com.lsnju.base.model.PageList;
import com.lsnju.base.util.TpStringUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * ${tableMo.repoMo.implName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Slf4j
@Setter
@Repository
public class ${tableMo.repoMo.implName} implements ${tableMo.repoMo.apiName} {

    @Autowired
    private ${tableMo.daoMo.apiName} dao;

    @Override
    public ${tableMo.idColumn.propType.type} add(${tableMo.repoMo.modelName} item) {
        log.info("{}", item);
        Objects.requireNonNull(item);
        return this.dao.add(Converter.convert(item));
    }

    @Override
    public ${tableMo.repoMo.modelName} getById(${tableMo.idColumn.propType.type} id) {
        log.info("id={}", id);
        return Converter.convert(this.dao.getById(id));
    }

    @Override
    public ${tableMo.repoMo.modelName} lockById(${tableMo.idColumn.propType.type} id) {
        log.info("id={}", id);
        return Converter.convert(this.dao.lockById(id));
    }

    @Override
    public boolean update(${tableMo.repoMo.modelName} item) {
        log.info("{}", item);
        Objects.requireNonNull(item);
        return this.dao.update(Converter.convert(item)) == 1;
    }

    @Override
    public boolean delete(${tableMo.idColumn.propType.type} id) {
        log.info("delete id={}", id);
        return this.dao.delete(id) == 1;
    }

    @Override
    public PageList<${tableMo.repoMo.modelName}> pageQuery(${tableMo.repoMo.pageReqName} req) {
        log.info("{}", req);
        ${tableMo.daoMo.pageReqName} request = new ${tableMo.daoMo.pageReqName}();
        BeanUtils.copyProperties(req, request);
        request.setPageSize(req.getPageSize());
        request.setPageNum(req.getPageNum());
        return new PageList<>(this.dao.pageQuery(request), Converter::convert);
    }

    @Override
    public List<${tableMo.repoMo.modelName}> getByCond(${tableMo.repoMo.qryReqName} req) {
        log.info("{}", req);
        ${tableMo.daoMo.qryReqName} request = new ${tableMo.daoMo.qryReqName}();
        BeanUtils.copyProperties(req, request);
        return Converter.convert(dao.getByCond(request));
    }

    /**
     * Converter
     */
    protected static class Converter {

        public static ${tableMo.repoMo.modelName} convert(${tableMo.daoMo.modelName} a) {
            if (a == null) {
                return null;
            }
            ${tableMo.repoMo.modelName} r = new ${tableMo.repoMo.modelName}();
            BeanUtils.copyProperties(a, r);
            <#list tableMo.dataColumnList as col>
            r.${col.propSetMethod}(a.${col.propGetMethod}());
            </#list>
            // TODO add more
            return r;
        }

        public static ${tableMo.daoMo.modelName} convert(${tableMo.repoMo.modelName} a) {
            if (a == null) {
                return null;
            }
            ${tableMo.daoMo.modelName} r = new ${tableMo.daoMo.modelName}();
            BeanUtils.copyProperties(a, r);
            <#list tableMo.dataColumnList as col>
            <#if col.propType == 'STRING' && tableMo.dbMo.notNull>
            r.${col.propSetMethod}(TpStringUtils.fixLen(a.${col.propGetMethod}(), ${col.maxLength}));
            <#else>
            r.${col.propSetMethod}(a.${col.propGetMethod}());
            </#if>
            </#list>
            // TODO add more
            return r;
        }

        public static List<${tableMo.repoMo.modelName}> convert(List<${tableMo.daoMo.modelName}> list) {
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyList();
            }
            return list.stream().map(Converter::convert).collect(Collectors.toList());
        }
    }

}
