package com.cjt.common.dto;

import java.util.List;

public class ResultDto {

  private int code;

  private List datas;

  private Object data;

  private String desc;

  public ResultDto(int code, Object data, String desc) {
    this.code = code;
    this.data = data;
    this.desc = desc;
  }

  public ResultDto(int code, List datas, String desc) {
    this.code = code;
    this.datas = datas;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public List getDatas() {
    return datas;
  }

  public void setDatas(List datas) {
    this.datas = datas;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
