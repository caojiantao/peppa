package com.cjt.entity.model.system;

import lombok.Data;

/**
 * @author caojiantao
 */
@Data
public class DictValue {

    private Integer id;
    private Integer setId;
    private String name;
    private String value;
    private Integer order;
    private String desc;
}
