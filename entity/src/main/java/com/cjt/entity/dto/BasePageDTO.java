package com.cjt.entity.dto;

import lombok.Data;

/**
 * 分页基础DTO
 *
 * @author caojiantao
 */
@Data
public class BasePageDTO {

    private Integer start;
    private Integer offset;
}
