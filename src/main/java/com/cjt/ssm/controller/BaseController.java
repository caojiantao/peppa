package com.cjt.ssm.controller;

import com.cjt.ssm.util.ExceptionUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author caojiantao
 * @create 2017-07-22 18:41:02
 * @desc controller的基类
 */
public class BaseController {

  public HttpServletRequest request;

  public HttpServletResponse response;

  public HttpSession session;

  /**
   * 该注释表示每个该类及子类的请求调用之前都回执行该方法
   */
  @ModelAttribute
  public void initReqResSession(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
    this.session = request.getSession();
  }

  /**
   * 采用注解方式统一处理异常
   */
  @ExceptionHandler
  public String handleException(HttpServletRequest request, Exception ex) {
    request.setAttribute("error", ExceptionUtil.toDetailStr(ex));
    request.setAttribute("test", "00000");
    return "error";
  }
}
