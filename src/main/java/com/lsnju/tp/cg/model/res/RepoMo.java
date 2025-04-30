package com.lsnju.tp.cg.model.res;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.lsnju.base.model.BaseMo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/17 20:32
 * @version V1.0
 */
@Getter
@Setter
public class RepoMo extends BaseMo {

    private String modelName;
    private String modelPackage;
    private String modelPath;

    private String apiName;
    private String apiPackage;
    private String apiPath;

    private String implName;
    private String implPackage;
    private String implPath;

    private String pageReqName;
    private String pageReqPackage;
    private String pageReqPath;

    private String qryReqName;
    private String qryReqPackage;
    private String qryReqPath;

    private String testName;
    private String testPackage;
    private String testPath;

    @ToStringExclude
    private List<GenFileMo> genFileList;

}
