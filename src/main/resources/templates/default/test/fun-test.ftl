package ${tableMo.repoMo.testPackage};

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ${tableMo.repoMo.modelPackage}.${tableMo.repoMo.modelName};
import ${tableMo.repoMo.pageReqPackage}.${tableMo.repoMo.pageReqName};
import ${tableMo.repoMo.qryReqPackage}.${tableMo.repoMo.qryReqName};

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
@Slf4j
@Setter
@SpringBootTest
@ActiveProfiles("test")
public class ${tableMo.repoMo.testName} {

    @Autowired
    private ${tableMo.repoMo.apiName} repo;

    @Test
    void test_getById() {
        ${tableMo.repoMo.modelName} r = repo.getById(${tableMo.idColumn.propType.defaultValue});
        log.info("{}", r);
    }

    @Test
    void test_pageQuery() {
        log.info("");
        final ${tableMo.repoMo.pageReqName} req = new ${tableMo.repoMo.pageReqName}();
        req.setPageSize(2);
        log.info("{}", repo.pageQuery(req));
    }

    @Test
    void test_getByCond() {
        log.info("");
        final ${tableMo.repoMo.qryReqName} req = new ${tableMo.repoMo.qryReqName}();
        log.info("{}", repo.getByCond(req));
    }

}
