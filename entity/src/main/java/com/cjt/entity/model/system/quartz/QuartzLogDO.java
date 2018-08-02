package com.cjt.entity.model.system.quartz;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzLogDO extends BaseDO {

    private Integer quartzId;
    private Integer status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMsg;
}
