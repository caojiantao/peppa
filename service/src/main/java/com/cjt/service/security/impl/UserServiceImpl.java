package com.cjt.service.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.algorithms.Algorithm;
import com.cjt.common.dto.UserDTO;
import com.cjt.common.util.ExceptionUtil;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.security.IMenuDAO;
import com.cjt.dao.security.IRoleDAO;
import com.cjt.dao.security.IUserDAO;
import com.cjt.entity.security.User;
import com.cjt.service.TokenService;
import com.cjt.service.security.IUserService;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Autowired
    private TokenService tokenService;

    @Value("${password_secret}")
    private String passwordSecret;

    @Override
    public User login(String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordSecret);
            byte[] bytes = algorithm.sign(password.getBytes(CharEncoding.UTF_8));
            password = Hex.encodeHexString(bytes);
            return userDAO.login(username, password);
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.toDetailStr(e));
            return null;
        }
    }

    @Override
    public User getUserByUserId(long userId) {
        UserDTO.Builder builder = new UserDTO.Builder();
        List<User> users = getUserByDTO(builder.id(userId).build());
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public List<User> getUserByDTO(UserDTO userDto) {
        return userDAO.getUserByDTO(userDto);
    }

    @Override
    public JSONObject listUserByPage(UserDTO userDTO) {
        List<User> users = userDAO.listUser(userDTO);
        int total = userDAO.countUser(userDTO);
        return JsonUtils.toPageData(users, total);
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