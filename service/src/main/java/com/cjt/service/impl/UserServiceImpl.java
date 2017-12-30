package com.cjt.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.cjt.common.dto.UserDto;
import com.cjt.common.util.ExceptionUtil;
import com.cjt.dao.demo.IUserDao;
import com.cjt.entity.demo.User;
import com.cjt.service.IUserService;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.CharEncoding.UTF_8;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    private IUserDao userDao;

    @Value("${password_secret}")
    private String passwordSecret;

    @Override
    public boolean login(String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passwordSecret);
            byte[] bytes = algorithm.sign(password.getBytes(UTF_8));
            password = Hex.encodeHexString(bytes);
            return userDao.login(username, password);
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.toDetailStr(e));
            return false;
        }
    }

    @Override
    public User getUserByDto(UserDto userDto) {
        return userDao.getUserByDto(userDto);
    }

    @Override
    public boolean existAccount(String account) {
        return userDao.existAccount(account);
    }

    @Override
    public List<User> listAllUsers() {
        return userDao.findAll();
    }

    /**
     * 若发生checked exception(IOException且抛出)不会发生回滚，可以采用setRollbackOnly
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void saveUsers(List<User> users) {
        userDao.saveUserBatch(users);
    }

    @Override
    public void updateUsers(List<User> users) {
        List<Long> ids = new ArrayList<>();
        for (User user : users) {
            ids.add(user.getId());
        }
        userDao.updateUserBatch(users, ids);
    }
}