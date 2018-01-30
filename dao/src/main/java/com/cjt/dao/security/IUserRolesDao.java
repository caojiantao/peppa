package com.cjt.dao.security;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserRolesDao {

    /**
     * 获取指定用户ID的角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    List<Integer> listRoleIdByUserId(int userId);

    /**
     * 保存 用户-角色 对应关系
     *
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     */
    void saveUserRoles(@Param("userId") long userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 删除 用户-角色 对应关系
     *
     * @param userId 用户ID
     * @return 成功与否
     */
    int removeUserRoles(long userId);
}
