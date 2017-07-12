package com.cjt.ssm.controller;

import com.cjt.ssm.model.User;
import com.cjt.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value="/")
public class TestController {

	@Resource
	private UserService userService;
	
	@RequestMapping(value="/test")
	@ResponseBody
	public List<User> test(){
        return userService.listAllUsers();
	}
}
