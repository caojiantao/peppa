package com.cjt.ssm.controller;

import com.cjt.ssm.entity.User;
import com.cjt.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/")
public class TestController extends BaseController {

	@Resource
	private UserService userService;

	@RequestMapping("login")
    public String login(){
	    return "login";
    }
	
	@RequestMapping("test")
	@ResponseBody
	public void test(){
		List<User> users = userService.listAllUsers();
		for(int i = 0; i < 2; i++){
			users.get(i).setAge(i);
		}
		userService.updateUsers(users.subList(0, 2));
	}

	@RequestMapping("/vm")
	public String vm(Model model){
	    model.addAttribute("name", "曹建涛");
		return "test";
	}

	@RequestMapping(value = "a/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getA(@PathVariable("id") String id){
	    return "get";
    }
}