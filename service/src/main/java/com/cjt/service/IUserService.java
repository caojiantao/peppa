package com.cjt.service;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.dto.UserDTO;
import com.cjt.entity.admin.security.User;

/**
 * @author caojiantao
 */
public interface IUserService {

    /**
     * 校验账号登录是否成功
     *
     * @param username 账号
     * @param password 密码
     * @return 成功与否
     */
    boolean login(String username, String password);

    /**
     * 通过username获取用户
     *
     * @param username 用户账号
     * @return 用户信息
     */
    User getUserByUsername(String username);

    /**
     * 通过userId获取用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserByUserId(long userId);

    /**
     * 通过dto获取用户
     *
     * @param userDTO 用户DTO
     * @return 用户信息
     */
    User getUserByDto(UserDTO userDTO);

    /**
     * 根据dto获取用户分页信息
     *
     * @param userDTO 用户DTO
     * @return 用户分页信息
     */
    JSONObject listUserByPage(UserDTO userDTO);

    /**
     * 新增用户
     *
     * @param user 新增用户信息
     * @return 成功与否
     */
    boolean saveUser(User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 成功与否
     */
    boolean updateUser(User user);

    /**
     * 删除指定ID的用户信息
     *
     * @param id 用户ID
     * @return 成功与否
     */
    boolean removeUserById(Long id);
}
