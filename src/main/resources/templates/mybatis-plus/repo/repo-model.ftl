package ${tableMo.repoMo.modelPackage};

import com.lsnju.base.model.BaseMo;

import lombok.Getter;
import lombok.Setter;

/**
 * ${tableMo.repoMo.modelName}
 * ${tableMo.tableName} : ${tableMo.tableComment}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Getter
@Setter
public class ${tableMo.repoMo.modelName} extends BaseMo {

<#list tableMo.columnList as col>
    private ${col.propType.type} ${col.propName}; // ${col.columnComment!}
</#list>

}
