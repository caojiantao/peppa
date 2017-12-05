package com.cjt.common.dto;

import java.util.Map;

public class ResultDto {

  private int code;

  private Map<String, Object> data;

  private String msg;

  public ResultDto(int code, Map<String, Object> data, String msg) {
    this.code = code;
    this.data = data;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
