package com.cjt.service.security.impl;

import com.caojiantao.common.encrypt.Md5Utils;
import com.caojiantao.common.util.CollectionUtils;
import com.cjt.dao.security.IUserDAO;
import com.cjt.dao.security.IUserRolesDao;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.security.User;
import com.cjt.service.security.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务处理service
 *
 * @author caojiantao
 */
@Service
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    private final IUserRolesDao userRolesDao;

    @Value("${password_secret}")
    private String passwordSecret;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO, IUserRolesDao userRolesDao) {
        this.userDAO = userDAO;
        this.userRolesDao = userRolesDao;
    }

    /**
     * 对密码进行MD5私盐不可逆加密
     */
    private String encryptPassword(String password) {
        return Md5Utils.md5(password, passwordSecret);
    }

    @Override
    public User login(String username, String password) {
        return userDAO.login(username, encryptPassword(password));
    }

    @Override
    public User getUserById(int id) {
        UserDTO.Builder builder = new UserDTO.Builder();
        List<User> users = getUsers(builder.id(id).build());
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public List<User> getUsers(UserDTO userDTO) {
        return userDAO.getDatas(userDTO);
    }

    @Override
    public int getUsersTotal(UserDTO userDTO) {
        return userDAO.getDatasTotal(userDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(User user, List<Integer> roleIds) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        if (user.getId() == null){
            userDAO.insert(user);
            if (CollectionUtils.isNotEmpty(roleIds)) {
                userRolesDao.saveUserRoles(user.getId(), roleIds);
            }
            return user.getId() > 0;
        } else {
            userRolesDao.removeUserRoles(user.getId());
            userRolesDao.saveUserRoles(user.getId(), roleIds);
            return userDAO.updateById(user) > 0;
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        return userDAO.deleteById(id) > 0;
    }
}