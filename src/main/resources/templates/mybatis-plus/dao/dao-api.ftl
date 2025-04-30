package ${tableMo.daoMo.apiPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName};

/**
 * <p>
 * ${tableMo.tableName} : ${tableMo.tableComment}
 * ${tableMo.daoMo.apiName}
 * </p>
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
public interface ${tableMo.daoMo.apiName} extends BaseMapper<${tableMo.daoMo.modelName}> {

    ${tableMo.daoMo.modelName} lockById(${tableMo.idColumn.propType.type} id);

}
