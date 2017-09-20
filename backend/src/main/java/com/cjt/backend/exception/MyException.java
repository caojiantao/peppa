package com.cjt.backend.exception;

/**
 * @author caojiantao
 * @create 2017-08-05 14:10:20
 * @desc 异常测试类
 */
public class MyException extends Exception {

  private String msg;

  public MyException(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return "MyException{" +
            "msg='" + msg + '\'' +
            '}';
  }
}
