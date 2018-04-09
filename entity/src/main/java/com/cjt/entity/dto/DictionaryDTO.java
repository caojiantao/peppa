package com.cjt.entity.dto;

/**
 * @author caojiantao
 */
public class DictionaryDTO extends BasePageDTO {

    private String code;

    private Integer setId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    @Override
    public String toString() {
        return "DictionaryDTO{" +
                "code='" + code + '\'' +
                ", setId=" + setId +
                "} " + super.toString();
    }
}
