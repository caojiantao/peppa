package com.cjt.entity.model.system.dictionary;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictValueDO extends BaseDO {

    private Integer setId;
    private String name;
    private String value;
    private Integer order;
    private String desc;
}
