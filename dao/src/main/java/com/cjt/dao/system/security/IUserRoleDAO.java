package com.cjt.dao.system.security;

import com.cjt.entity.model.system.security.UserRoleDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserRoleDAO {

    List<Integer> listRoleIdByUserId(int userId);

    void saveUserRoles(List<UserRoleDO> userRoles);

    int deleteByUserId(long userId);
}
