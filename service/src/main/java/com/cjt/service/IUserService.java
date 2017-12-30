package com.cjt.service;

import com.cjt.common.dto.UserDto;
import com.cjt.entity.demo.User;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserService {

    boolean login(String username, String password);

    User getUserByDto(UserDto userDto);

    boolean existAccount(String account);

    List<User> listAllUsers();

    void saveUser(User user);

    void saveUsers(List<User> users);

    void updateUsers(List<User> users);
}
