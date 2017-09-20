package com.cjt.backend.service.impl;

import com.cjt.backend.service.UserService;
import com.cjt.dao.UserDao;
import com.cjt.common.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserDao userDao;

  public List<User> listAllUsers() {
    return userDao.findAll();
  }

  @Transactional
  public void saveUser(User user) {
    try {
      userDao.saveUser(user);
    } catch (Exception e) {
      // 只有显式抛出runtime exception异常或者未被try catch包裹，事务才能回滚，因而这里采取手动回滚
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
  }

  public void saveUsers(List<User> users) {
    userDao.saveUserBatch(users);
  }

  public void updateUsers(List<User> users) {
    List<Integer> ids = new ArrayList<Integer>();
    for (User user : users) {
      ids.add(user.getId());
    }
    userDao.updateUserBatch(users, ids);
  }
}