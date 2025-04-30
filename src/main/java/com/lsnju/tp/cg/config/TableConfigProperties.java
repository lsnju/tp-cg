package com.lsnju.tp.cg.config;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;
import com.lsnju.base.model.BaseMo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/17 12:27
 * @version V1.0
 */
@Getter
@Setter
public class TableConfigProperties extends BaseMo {

    private static final String SEPARATOR_CHARS = ",";

    private String ignoreTablePrefix;
    private String ignoreTableSuffix;

    private String ignoreFieldPrefix;
    private String ignoreFieldSuffix;

    private String createField = "gmt_create,create_date";
    private String modifyField = "gmt_modify,update_date";
    private String idField = "id";

    public Set<String> idSet() {
        return Sets.newHashSet(StringUtils.split(idField, SEPARATOR_CHARS));
    }

    public Set<String> createSet() {
        return Sets.newHashSet(StringUtils.split(createField, SEPARATOR_CHARS));
    }

    public Set<String> modifySet() {
        final String[] split = StringUtils.split(modifyField, SEPARATOR_CHARS);
        if (ArrayUtils.isEmpty(split)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(split);
    }

    public Set<String> ignoreTablePrefixSet() {
        final String[] split = StringUtils.split(ignoreTablePrefix, SEPARATOR_CHARS);
        if (ArrayUtils.isEmpty(split)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(split);
    }

    public Set<String> ignoreTableSuffixSet() {
        final String[] split = StringUtils.split(ignoreTableSuffix, SEPARATOR_CHARS);
        if (ArrayUtils.isEmpty(split)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(split);
    }

    public Set<String> ignoreFieldPrefixSet() {
        final String[] split = StringUtils.split(ignoreFieldPrefix, SEPARATOR_CHARS);
        if (ArrayUtils.isEmpty(split)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(split);
    }

    public Set<String> ignoreFieldSuffixSet() {
        final String[] split = StringUtils.split(ignoreFieldSuffix, SEPARATOR_CHARS);
        if (ArrayUtils.isEmpty(split)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(split);
    }

}
