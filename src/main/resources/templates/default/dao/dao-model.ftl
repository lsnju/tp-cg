package ${tableMo.daoMo.modelPackage};

import com.lsnju.base.model.BaseDo;

import lombok.Getter;
import lombok.Setter;

/**
 * ${tableMo.tableName} : ${tableMo.tableComment}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Getter
@Setter
public class ${tableMo.daoMo.modelName} extends BaseDo {

<#list tableMo.columnList as col>
    private ${col.propType.type} ${col.propName}; // ${col.columnComment!}
</#list>

}
