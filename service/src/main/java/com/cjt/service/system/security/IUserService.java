package com.cjt.service.system.security;

import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.system.security.UserDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserService {

    UserDO login(String username, String password);

    UserDO getUserById(int id);

    List<UserDO> listUsers(UserDTO userDTO);

    int countUsers(UserDTO userDTO);

    boolean saveUser(UserDO user, List<Integer> roleIds);

    boolean deleteUserById(int id);
}
