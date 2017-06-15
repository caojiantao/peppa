package com.cjt.ssm;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cjt.ssm.dao.UserDao;
import com.cjt.ssm.model.User;

public class Test {

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UserDao userDao = ctx.getBean("userDao", UserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
	}
}
