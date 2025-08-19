package ${tableMo.daoMo.apiPackage};

import java.util.List;

import ${tableMo.daoMo.pageReqPackage}.${tableMo.daoMo.pageReqName};
import ${tableMo.daoMo.qryReqPackage}.${tableMo.daoMo.qryReqName};
import ${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName};
import com.lsnju.base.model.PageList;

/**
 * ${tableMo.daoMo.apiName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
public interface ${tableMo.daoMo.apiName} {

    ${tableMo.idColumn.propType.type} add(${tableMo.daoMo.modelName} a${tableMo.daoMo.modelName});

    ${tableMo.daoMo.modelName} getById(${tableMo.idColumn.propType.type} id);

    ${tableMo.daoMo.modelName} lockById(${tableMo.idColumn.propType.type} id);

    ${tableMo.daoMo.modelName} lockByIdWait(${tableMo.idColumn.propType.type} id);

    ${tableMo.daoMo.modelName} lockByIdNoWait(${tableMo.idColumn.propType.type} id);

    int update(${tableMo.daoMo.modelName} a${tableMo.daoMo.modelName});

    int delete(${tableMo.idColumn.propType.type} id);

    PageList<${tableMo.daoMo.modelName}> pageQuery(${tableMo.daoMo.pageReqName} req);

    List<${tableMo.daoMo.modelName}> getByCond(${tableMo.daoMo.qryReqName} req);

}
