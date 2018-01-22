package com.cjt.dao.admin.security;

import com.cjt.common.dto.UserDTO;
import com.cjt.entity.admin.security.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户实体数据持久层
 *
 * @author caojiantao
 */
public interface IUserDAO {

    /**
     * 校验账号登录是否成功
     *
     * @param username 账号
     * @param password 密码
     * @return 成功与否
     */
    boolean login(@Param("username") String username, @Param("password") String password);

    /**
     * 通过dto获取用户
     *
     * @param userDTO 用户DTO
     * @return 用户信息
     */
    User getUserByDto(UserDTO userDTO);

    /**
     * 根据dto列出所有用户信息
     *
     * @param userDTO 用户DTO
     * @return 所有用户信息
     */
    List<User> listUser(UserDTO userDTO);

    /**
     * 根据dto计算所有用户数量
     *
     * @param userDTO 用户DTO
     * @return 所有用户数量
     */
    int countUser(UserDTO userDTO);

    /**
     * 新增用户
     *
     * @param user 新增用户信息
     */
    void saveUser(User user);

    /**
     * 移除用户
     *
     * @param id 用户ID
     * @return 成功与否
     */
    int removeUserById(Long id);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int updateUser(User user);
}
