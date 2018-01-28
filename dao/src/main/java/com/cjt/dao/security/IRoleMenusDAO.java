package com.cjt.dao.security;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleMenusDAO {

    List<Integer> listMenuIdByRoleId(int roleId);

    /**
     * 批量插入
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     */
    void saveRoleMenus(@Param("roleId") int roleId, @Param("menuIds") List<Integer> menuIds);

    int removeRoleMenus(int roleId);
}
