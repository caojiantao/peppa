package com.cjt.service;

import com.cjt.entity.demo.User;

import java.util.List;

public interface UserService {

  boolean login(String account, String password);

  boolean existAccount(String account);

  List<User> listAllUsers();

  void saveUser(User user);

  void saveUsers(List<User> users);

  void updateUsers(List<User> users);
}
