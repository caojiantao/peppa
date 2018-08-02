package com.cjt.dao.system.security;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.model.system.security.RoleMenuDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleMenuDAO extends IBaseDAO<RoleMenuDO, Object> {

    List<Integer> listMenuIdsByRoleId(int roleId);

    void saveRoleMenus(List<RoleMenuDO> roleMenus);

    int deleteByRoleId(int roleId);
}
