package com.cjt.admin.controller;

import com.cjt.common.dto.UserDto;
import com.cjt.entity.demo.User;
import com.cjt.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Resource
  private IUserService userService;

  @GetMapping("/{userId}")
  public Object getUser(@PathVariable("userId") Long userId){
    UserDto.Builder builder = new UserDto.Builder();
    User user = userService.getUserByDto(builder.userId(userId).create());
    if (user != null){
      response.setStatus(HttpStatus.OK.value());
    } else {
      response.setStatus(HttpStatus.NOT_FOUND.value());
    }
    return user;
  }
}
