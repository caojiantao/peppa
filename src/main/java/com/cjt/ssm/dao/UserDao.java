package com.cjt.ssm.dao;

import java.util.List;

import com.cjt.ssm.model.User;

public interface UserDao {

	List<User> findAll();
}
