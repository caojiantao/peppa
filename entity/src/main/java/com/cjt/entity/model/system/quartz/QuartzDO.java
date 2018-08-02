package com.cjt.entity.model.system.quartz;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时计划基本信息
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzDO extends BaseDO {

    private String name;
    private String group;
    private Boolean status;
    private String cronExpression;
    private String desc;
    private String jobClass;
    private Boolean deleted;
}
