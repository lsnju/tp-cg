package com.lsnju.tp.cg.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.lsnju.base.model.BaseMo;
import com.lsnju.tp.cg.model.enums.DbTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lisong
 * @since 2023/9/8 11:08
 * @version V1.0
 */
public class DbMo extends BaseMo {

    @Getter
    @Setter
    private DbTypeEnum dbTypeEnum;
    @Getter
    @Setter
    private String driverName;
    @Getter
    @Setter
    private String driverVersion;
    @Getter
    @Setter
    private String productName;
    @Getter
    @Setter
    private String productVersion;
    @Getter
    @Setter
    private int majorVersion;
    @Getter
    @Setter
    private int minorVersion;
    @Getter
    @Setter
    private boolean supportNoWait;
    @Getter
    @Setter
    private boolean notNull;

    private transient final Set<String> keywords = new HashSet<>();

    {
        addKeyword("select");
        addKeyword("delete");
        addKeyword("drop");
        addKeyword("truncate");
        addKeyword("table");
        addKeyword("create");
    }

    public void addKeyword(String keyword) {
        this.keywords.add(StringUtils.upperCase(keyword));
    }

    public boolean containsKeyword(String keyword) {
        return this.keywords.contains(StringUtils.upperCase(keyword));
    }

}
