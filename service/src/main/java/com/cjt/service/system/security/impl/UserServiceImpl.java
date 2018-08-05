package com.cjt.service.system.security.impl;

import com.caojiantao.common.encrypt.Md5Utils;
import com.caojiantao.common.util.CollectionUtils;
import com.cjt.dao.system.security.IUserDAO;
import com.cjt.dao.system.security.IUserRoleDAO;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.system.security.UserDO;
import com.cjt.entity.model.system.security.UserRoleDO;
import com.cjt.service.system.security.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 用户业务处理service
 *
 * @author caojiantao
 */
@Service
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    private final IUserRoleDAO userRolesDao;

    @Value("${password_secret}")
    private String passwordSecret;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO, IUserRoleDAO userRolesDao) {
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
    public UserDO login(String username, String password) {
        return userDAO.login(username, encryptPassword(password));
    }

    @Override
    public UserDO getUserById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public List<UserDO> listUsers(UserDTO userDTO) {
        return userDAO.listObjects(userDTO);
    }

    @Override
    public int countUsers(UserDTO userDTO) {
        return userDAO.countObjects(userDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(UserDO user, List<Integer> roleIds) {
        LocalDateTime now = LocalDateTime.now();
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        // 简单处理，先删除全部关联数据，后进行整体插入
        userRolesDao.deleteByUserId(user.getId());
        if (user.getId() == null) {
            user.setGmtCreate(now);
            userDAO.insert(user);
            saveUserRoles(user.getId(), roleIds, now);
            return user.getId() > 0;
        } else {
            saveUserRoles(user.getId(), roleIds, now);
            return userDAO.updateById(user) > 0;
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        return userDAO.deleteById(id) > 0;
    }

    private void saveUserRoles(int userId, List<Integer> roleIds, LocalDateTime now) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<UserRoleDO> userRoles = Collections.emptyList();
            roleIds.forEach(roleId -> {
                UserRoleDO userRole = new UserRoleDO();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setGmtCreate(now);
                userRoles.add(userRole);
            });
            userRolesDao.saveUserRoles(userRoles);
        }
    }
}