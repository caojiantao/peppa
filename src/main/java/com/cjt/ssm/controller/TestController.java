package com.cjt.ssm.controller;

import com.cjt.ssm.dto.ResultMsg;
import com.cjt.ssm.entity.User;
import com.cjt.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/")
public class TestController extends BaseController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/test")
	@ResponseBody
	public ResultMsg test(){
	    try {
            List<User> users = userService.listAllUsers();
            throw new Exception("fuck");
//            return initSuccessMsg(users);
        } catch (Exception e){
	        return initFailedMsg(e.getMessage());
        }
	}

	@RequestMapping("/vm")
	public String vm(Model model){
	    model.addAttribute("name", "曹建涛");
		return "test";
	}
}
