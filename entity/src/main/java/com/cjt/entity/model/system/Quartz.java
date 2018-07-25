package com.cjt.entity.model.system;

import lombok.Data;

/**
 * 定时计划基本信息
 *
 * @author caojiantao
 */
@Data
public class Quartz{

    private Integer id;
    private String name;
    private String group;
    private Boolean status;
    private String cronExpre;
    private String desc;
    private String jobClass;
    private Boolean deleted;
}
