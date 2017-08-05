package com.cjt.ssm.service;

import com.cjt.ssm.entity.User;

import java.util.List;

public interface UserService {

  List<User> listAllUsers();

  void saveUser(User user);

  void saveUsers(List<User> users);

  void updateUsers(List<User> users);
}
