package com.cjt.dao.security;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserRolesDao {

    /**
     * @param userId 用户ID
     * @return 该用户拥有的角色ID集合
     */
    List<Integer> listRoleIdByUserId(int userId);

    /**
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     */
    void saveUserRoles(@Param("userId") long userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * @param userId 用户ID
     * @return 成功与否
     */
    int removeUserRoles(long userId);
}
