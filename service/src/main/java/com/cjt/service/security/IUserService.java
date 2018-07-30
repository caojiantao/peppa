package com.cjt.service.security;

import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.security.User;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserService {

    User login(String username, String password);

    User getUserById(int id);

    List<User> getUsers(UserDTO userDTO);

    int getUsersTotal(UserDTO userDTO);

    boolean saveUser(User user, List<Integer> roleIds);

    boolean deleteUserById(int id);
}
