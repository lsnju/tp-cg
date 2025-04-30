<#ftl strip_whitespace=true>
package ${tableMo.daoMo.modelPackage};

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsnju.base.model.BaseDo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * ${tableMo.tableName} : ${tableMo.tableComment}
 * </p>
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("${tableMo.tableName}")
public class ${tableMo.daoMo.modelName} extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    <#if tableMo.idColumn??>
    @TableId(value = "${tableMo.idColumn.columnName}", type = IdType.AUTO)
    private ${tableMo.idColumn.propType.mpType} ${tableMo.idColumn.propName}; // ${tableMo.idColumn.columnComment!}
    </#if>
    <#list tableMo.dataColumnList as col>
    @TableField(value = "${col.columnName}")
    private ${col.propType.mpType} ${col.propName}; // ${col.columnComment!}
    </#list>
    <#if tableMo.gmtCreate??>
    @TableField(value = "${tableMo.gmtCreate.columnName}")
    private ${tableMo.gmtCreate.propType.mpType} ${tableMo.gmtCreate.propName}; // ${tableMo.gmtCreate.columnComment!}
    </#if>
    <#if tableMo.gmtModify??>
    @TableField(value = "${tableMo.gmtModify.columnName}")
    private ${tableMo.gmtModify.propType.mpType} ${tableMo.gmtModify.propName}; // ${tableMo.gmtModify.columnComment!}
    </#if>

}
