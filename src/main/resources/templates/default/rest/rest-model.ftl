package ${tableMo.restMo.modelPackage};

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import ${tableMo.repoMo.modelPackage}.${tableMo.repoMo.modelName};
import com.lsnju.base.model.BaseMo;

import lombok.Getter;
import lombok.Setter;

/**
 * ${tableMo.restMo.modelName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Getter
@Setter
public class ${tableMo.restMo.modelName} extends BaseMo {

<#list tableMo.columnList as col>
<#if col.propType == 'DATE' >
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
</#if>
    private ${col.propType.type} ${col.propName}; // ${col.columnComment!}
</#list>
    // TODO add/remove properties here

    public ${tableMo.restMo.modelName}(${tableMo.repoMo.modelName} a${tableMo.repoMo.modelName}) {
        BeanUtils.copyProperties(a${tableMo.repoMo.modelName}, this);
        // TODO
    }

}
