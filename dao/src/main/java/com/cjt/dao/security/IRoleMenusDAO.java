package com.cjt.dao.security;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleMenusDAO {

    /**
     * 获取指定ID的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Integer> listMenuIdByRoleId(int roleId);

    /**
     * 批量插入
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     */
    void saveRoleMenus(@Param("roleId") int roleId, @Param("menuIds") List<Integer> menuIds);

    /**
     * 删除角色ID的 角色-菜单 关联关系
     *
     * @param roleId 角色ID
     * @return 成功与否
     */
    int removeRoleMenus(int roleId);
}
