package com.cjt.ssm.dao;

import com.cjt.ssm.entity.User;

import java.util.List;

public interface UserDao {

	List<User> findAll();
}
