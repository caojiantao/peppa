package com.cjt.entity.model.system;

import lombok.Data;

/**
 * @author caojiantao
 */
@Data
public class DictSet {

    private Integer id;
    private Integer parentValueId;
    private String code;
    private String name;
    private String desc;
}
