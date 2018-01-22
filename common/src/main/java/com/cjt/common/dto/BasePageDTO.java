package com.cjt.common.dto;

/**
 * 分页基础DTO
 *
 * @author caojiantao
 */
public class BasePageDTO {

    private Integer start;

    private Integer offset;

    public int getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "BasePageDTO{" +
                "start=" + start +
                ", offset=" + offset +
                '}';
    }
}
