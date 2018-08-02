package com.cjt.entity.model.system.dictionary;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictSetDO extends BaseDO {

    private Integer parentValueId;
    private String code;
    private String name;
    private String desc;
}
