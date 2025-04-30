package ${tableMo.restMo.apiPackage};

import javax.validation.Valid;

import org.springframework.cloud.openfeign.SpringQueryMap;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * ${tableMo.restMo.apiName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Api(tags = "【${genConfig.moduleName}】: ${tableMo.restMo.apiName}")
@RequestMapping(ApiConstants.BASE_URL + "/${tableMo.restMo.apiName}")
public interface ${tableMo.restMo.apiName} {

    @ApiOperation("分页查询")
    @GetMapping()
    ResponseEntity<BaseResp<PageList<${tableMo.restMo.modelName}>>> pageQuery(@Valid @SpringQueryMap ${tableMo.restMo.pageReqName} request);

    @ApiOperation("新增")
    @PostMapping()
    ResponseEntity<BaseResp<Long>> create(@Valid @RequestBody ${tableMo.restMo.addReqName} request);

    @ApiOperation("查询")
    @GetMapping("/{id}")
    ResponseEntity<BaseResp<${tableMo.restMo.modelName}>> query(@ApiParam(name = "id", value = "ID", required = true, example = "111")
                                              @PathVariable(name = "id") long id);

    @ApiOperation("更新")
    @PutMapping("/{id}")
    ResponseEntity<BaseResp<Void>> update(@ApiParam(name = "id", value = "ID", required = true, example = "111")
                                          @PathVariable(name = "id") long id,
                                          @Valid @RequestBody ${tableMo.restMo.addReqName} req);

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    ResponseEntity<BaseResp<Void>> delete(@ApiParam(name = "id", value = "ID", required = true, example = "111")
                                          @PathVariable(name = "id") long id);

}
