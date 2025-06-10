package ${tableMo.restMo.apiPackage};

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ${tableMo.restMo.basePackage}.ApiConstants;
import ${tableMo.restMo.addReqPackage}.${tableMo.restMo.addReqName};
import ${tableMo.restMo.pageReqPackage}.${tableMo.restMo.pageReqName};
import ${tableMo.restMo.modelPackage}.${tableMo.restMo.modelName};
import com.lsnju.base.model.PageList;
import com.lsnju.base.model.rs.BaseResp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ${tableMo.restMo.apiName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Tag(name = "【${genConfig.moduleName}】: ${tableMo.restMo.apiName}")
@RequestMapping(ApiConstants.BASE_URL + "/${tableMo.restMo.apiName}")
public interface ${tableMo.restMo.apiName} {

    @Operation(summary = "分页查询")
    @GetMapping()
    ResponseEntity<BaseResp<PageList<${tableMo.restMo.modelName}>>> pageQuery(@Valid @ParameterObject ${tableMo.restMo.pageReqName} request);

    @Operation(summary = "新增")
    @PostMapping()
    ResponseEntity<BaseResp<Long>> create(@Valid @RequestBody ${tableMo.restMo.addReqName} request);

    @Operation(summary = "查询")
    @GetMapping("/{id}")
    ResponseEntity<BaseResp<${tableMo.restMo.modelName}>> query(@Parameter(description = "ID", required = true, example = "111")
                                              @PathVariable(name = "id") ${tableMo.idColumn.propType.type} id);

    @Operation(summary = "更新")
    @PutMapping("/{id}")
    ResponseEntity<BaseResp<Void>> update(@Parameter(description = "ID", required = true, example = "111")
                                          @PathVariable(name = "id") ${tableMo.idColumn.propType.type} id,
                                          @Valid @RequestBody ${tableMo.restMo.addReqName} req);

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    ResponseEntity<BaseResp<Void>> delete(@Parameter(description = "ID", required = true, example = "111")
                                          @PathVariable(name = "id") ${tableMo.idColumn.propType.type} id);

}
