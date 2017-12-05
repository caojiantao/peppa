package com.cjt.admin.controller;

import com.cjt.common.dto.ResultDto;
import com.cjt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

  @Resource
  private UserService userService;

  /**
   * 登录创建会话
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  private ResultDto login(String account, String password){
    int code;
    boolean isLogin;
    String desc;
    if (userService.existAccount(account)){
      if (userService.login(account, password)){
        code = HttpStatus.OK.value();
        isLogin = true;
        desc = "登录成功";
      } else {
        code = HttpStatus.UNAUTHORIZED.value();
        isLogin = false;
        desc = "密码不正确";
      }
    } else {
      code = HttpStatus.NO_CONTENT.value();
      isLogin = false;
      desc = "该账号不存在";
    }
    return new ResultDto(code, isLogin, desc);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  private void logout(String account){
    System.out.println(account);
  }
}
