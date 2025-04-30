package com.lsnju.tp.cg.model.res;

import com.lsnju.base.model.BaseMo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ls
 * @since 2023/8/19 6:57
 * @version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenFileMo extends BaseMo {
    private String path;
    private String template;
}
