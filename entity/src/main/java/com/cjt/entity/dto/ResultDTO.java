package com.cjt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caojiantao
 */
@Data
@AllArgsConstructor
public class ResultDTO<T> {

    private Boolean success;
    private T data;
    private String msg;
}
