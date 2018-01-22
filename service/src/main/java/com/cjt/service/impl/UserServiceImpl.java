package com.cjt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.algorithms.Algorithm;
import com.cjt.common.dto.UserDTO;
import com.cjt.common.util.ExceptionUtil;
import com.cjt.dao.admin.security.IMenuDAO;
import com.cjt.dao.admin.security.IRoleDAO;
import com.cjt.dao.admin.security.IUserDAO;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.Role;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IUserService;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 用户业务处理service
 *
 * @author caojiantao
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IRoleDAO roleDAO;

    @Autowired
    private IMenuDAO menuDAO;

    @Value("${password_secret}")
    private String passwordSecret;

    @Override
    public boolean login(String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordSecret);
            byte[] bytes = algorithm.sign(password.getBytes(CharEncoding.UTF_8));
            password = Hex.encodeHexString(bytes);
            return userDAO.login(username, password);
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.toDetailStr(e));
            return false;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        UserDTO.Builder builder = new UserDTO.Builder();
        return this.getUserByDto(builder.username(username).build());
    }

    @Override
    public User getUserByDto(UserDTO userDto) {
        User user = userDAO.getUserByDto(userDto);
        List<Role> roles = roleDAO.listRoleByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public JSONObject listUserByPage(UserDTO userDTO) {
        JSONObject object = new JSONObject();
        List<User> users = userDAO.listUser(userDTO);
        object.put("data", users);
        int total = userDAO.countUser(userDTO);
        object.put("total", total);
        return object;
    }

    @Override
    public boolean saveUser(User user) {
        userDAO.saveUser(user);
        return user.getId() > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user) > 0;
    }

    @Override
    public boolean removeUserById(Long id) {
        return userDAO.removeUserById(id) > 0;
    }
}