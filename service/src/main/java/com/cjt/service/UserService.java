package com.cjt.service;

import com.cjt.entity.demo.User;

import java.util.List;

public interface UserService {

  List<User> listAllUsers();

  void saveUser(User user);

  void saveUsers(List<User> users);

  void updateUsers(List<User> users);
}
