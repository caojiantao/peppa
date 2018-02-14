package com.cjt.service.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.algorithms.Algorithm;
import com.cjt.common.util.ExceptionUtils;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.security.IUserDAO;
import com.cjt.dao.security.IUserRolesDao;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.security.User;
import com.cjt.service.security.IUserService;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IUserRolesDao userRolesDao;

    @Value("${password_secret}")
    private String passwordSecret;

    /**
     * 对密码进行加密
     */
    private String encryptPassword(String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordSecret);
            byte[] bytes = algorithm.sign(password.getBytes(CharEncoding.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
            return null;
        }
    }

    @Override
    public User login(String username, String password) {
        return userDAO.login(username, encryptPassword(password));
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(User user, List<Integer> roleIds) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        userDAO.saveUser(user);
        if (!roleIds.isEmpty()) {
            userRolesDao.saveUserRoles(user.getId(), roleIds);
        }
        return user.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(User user, List<Integer> roleIds) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        userRolesDao.removeUserRoles(user.getId());
        userRolesDao.saveUserRoles(user.getId(), roleIds);
        return userDAO.updateUser(user) > 0;
    }

    @Override
    public boolean removeUserById(Long id) {
        return userDAO.removeUserById(id) > 0;
    }
}