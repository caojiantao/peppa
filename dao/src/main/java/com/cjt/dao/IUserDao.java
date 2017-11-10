package com.cjt.dao;

import com.cjt.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDao {

  List<User> findAll();

  void saveUser(User user);

  void saveUserBatch(List<User> users);

  void updateUserBatch(@Param("users") List<User> users, @Param("ids") List<Integer> ids);
}
