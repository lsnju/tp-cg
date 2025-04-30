package ${tableMo.restMo.implPackage};

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ${tableMo.repoMo.apiPackage}.${tableMo.repoMo.apiName};
import ${tableMo.repoMo.modelPackage}.${tableMo.repoMo.modelName};
import ${tableMo.repoMo.pageReqPackage}.${tableMo.repoMo.pageReqName};
import ${tableMo.restMo.apiPackage}.${tableMo.restMo.apiName};
import ${tableMo.restMo.addReqPackage}.${tableMo.restMo.addReqName};
import ${tableMo.restMo.pageReqPackage}.${tableMo.restMo.pageReqName};
import ${tableMo.restMo.modelPackage}.${tableMo.restMo.modelName};
import com.lsnju.base.enums.BizErrorEnum;
import com.lsnju.base.model.PageList;
import com.lsnju.base.model.rs.BaseResp;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * ${tableMo.restMo.implName}
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Slf4j
@Setter
@RestController
public class ${tableMo.restMo.implName} implements ${tableMo.restMo.apiName} {

    @Autowired
    private ${tableMo.repoMo.apiName} repo;

    @Override
    public BaseResp<PageList<${tableMo.restMo.modelName}>> pageQuery(@Valid ${tableMo.restMo.pageReqName} request) {
        log.info("{}", request);
        final ${tableMo.repoMo.pageReqName} req = new ${tableMo.repoMo.pageReqName}();
        BeanUtils.copyProperties(request, req);
        req.setPageSize(request.getSize());
        req.setPageNum(request.getPage());
        final PageList<${tableMo.repoMo.modelName}> pageList = repo.pageQuery(req);
        if (pageList.getTotalCount() <= 0 || pageList.getPageNum() < request.getPage()) {
            return BaseResp.of(null, BizErrorEnum.NOT_FOUND);
        }
        final PageList<${tableMo.restMo.modelName}> ret = new PageList<>(pageList, ${tableMo.restMo.modelName}::new);
        return BaseResp.of(ret, BizErrorEnum.SUCCESS);
    }

    @Override
    public BaseResp<Long> create(@Valid ${tableMo.restMo.addReqName} request) {
        log.info("{}", request);
        return BaseResp.of(1L, BizErrorEnum.SUCCESS);
    }

    @Override
    public BaseResp<${tableMo.restMo.modelName}> query(long id) {
        log.info("{}", id);
        final ${tableMo.repoMo.modelName} item = repo.getById(id);
        if (item == null) {
            return BaseResp.of(null, BizErrorEnum.NOT_FOUND);
        }
        return BaseResp.of(new ${tableMo.restMo.modelName}(item), BizErrorEnum.SUCCESS);
    }

    @Override
    public BaseResp<Void> update(long id, @Valid ${tableMo.restMo.addReqName} request) {
        log.info("{}, request={}", id, request);
        final ${tableMo.repoMo.modelName} item = repo.getById(id);
        if (item == null) {
            return BaseResp.of(null, BizErrorEnum.NOT_FOUND);
        }
        return BaseResp.of(null, BizErrorEnum.SUCCESS);
    }

    @Override
    public BaseResp<Void> delete(long id) {
        log.info("{}", id);
        final ${tableMo.repoMo.modelName} item = repo.getById(id);
        if (item == null) {
            return BaseResp.of(null, BizErrorEnum.SUCCESS);
        }
        return BaseResp.of(null, BizErrorEnum.SUCCESS);
    }
}
