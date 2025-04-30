package ${tableMo.repoMo.apiPackage};

import java.util.List;

import ${tableMo.repoMo.modelPackage}.${tableMo.repoMo.modelName};
import ${tableMo.repoMo.pageReqPackage}.${tableMo.repoMo.pageReqName};
import ${tableMo.repoMo.qryReqPackage}.${tableMo.repoMo.qryReqName};
import com.lsnju.base.model.PageList;

/**
 * ${tableMo.repoMo.apiName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
public interface ${tableMo.repoMo.apiName} {

    ${tableMo.idColumn.propType.type} add(${tableMo.repoMo.modelName} item);

    ${tableMo.repoMo.modelName} getById(${tableMo.idColumn.propType.type} id);

    ${tableMo.repoMo.modelName} lockById(${tableMo.idColumn.propType.type} id);

    boolean update(${tableMo.repoMo.modelName} item);

    boolean delete(${tableMo.idColumn.propType.type} id);

    PageList<${tableMo.repoMo.modelName}> pageQuery(${tableMo.repoMo.pageReqName} req);

    List<${tableMo.repoMo.modelName}> getByCond(${tableMo.repoMo.qryReqName} req);

}
