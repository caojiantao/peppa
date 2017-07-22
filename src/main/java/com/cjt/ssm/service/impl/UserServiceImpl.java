package com.cjt.ssm.service.impl;

import com.cjt.ssm.dao.UserDao;
import com.cjt.ssm.entity.User;
import com.cjt.ssm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public List<User> listAllUsers() {
        return userDao.findAll();
    }
}
