package com.cjt.ssm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjt.ssm.dao.UserDao;
import com.cjt.ssm.model.User;

@Controller
@RequestMapping(value="/")
public class TestController {

	@Resource
	private UserDao userDao;
	
	@RequestMapping(value="/test")
	@ResponseBody
	public List<User> test(){
		List<User> users = userDao.findAll();
        return users;
	}
	
	@RequestMapping(value="/jsp")
	public String jsp(HttpServletRequest request, HttpServletResponse response, String name) throws IOException{
		System.out.println(name);
		return "曹建涛";
	}
	
	@RequestMapping(value="/res")
	@ResponseBody
	public String res(HttpServletRequest request, HttpServletResponse response, String name) throws IOException{
		/*response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("曹建涛 hello");
		writer.flush();
		writer.close();*/
		
		return "曹建涛";
	}
}
