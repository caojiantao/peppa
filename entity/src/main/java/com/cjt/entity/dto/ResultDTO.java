package com.cjt.entity.dto;

/**
 * @author caojiantao
 */
public class ResultDTO {

    private Boolean success;

    private String msg;

    public ResultDTO(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
