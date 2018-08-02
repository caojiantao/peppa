package com.cjt.entity.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@Data
public class BaseDO {

    private Integer id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
