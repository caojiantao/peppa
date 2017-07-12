package com.cjt.ssm.dao;

import java.util.List;

import com.cjt.ssm.model.User;
import org.springframework.stereotype.Repository;

public interface UserDao {

	List<User> findAll();
}
