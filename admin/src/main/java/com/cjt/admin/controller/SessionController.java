package com.cjt.admin.controller;

import com.cjt.common.dto.ResultDto;
import com.cjt.entity.demo.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

  /**
   * 登录创建会话
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  private ResultDto login(User user){
    System.out.println(user.toString());
    return new ResultDto(HttpStatus.CONFLICT.value(), null, "请求成功");
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  private void logout(String account){
    System.out.println(account);
  }
}
