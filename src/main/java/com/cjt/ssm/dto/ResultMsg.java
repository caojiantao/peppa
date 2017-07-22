package com.cjt.ssm.dto;

import java.util.List;

/**
 * @author caojiantao
 * @create 2017-07-22 18:39:00
 * @desc 后台响应前台ajax请求统一返回对象
 */
public class ResultMsg<T>{

    private boolean success;

    private List<T> data;

    private String msg;

    public ResultMsg(boolean success, List<T> data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public ResultMsg(boolean success, List<T> data) {
        this.success = success;
        this.data = data;
    }

    public ResultMsg(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
