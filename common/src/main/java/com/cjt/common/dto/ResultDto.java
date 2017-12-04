package com.cjt.common.dto;

import java.util.List;

public class ResultDto {

  private int code;

  private List data;

  private String desc;

  public ResultDto(int code, List data, String desc) {
    this.code = code;
    this.data = data;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public List getData() {
    return data;
  }

  public void setData(List data) {
    this.data = data;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
