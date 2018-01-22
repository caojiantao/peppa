package com.cjt.dao.admin.security;

import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.Role;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuDAO {

    List<Menu> listMenus();

    /**
     * 根据角色列表，获取菜单集合
     *
     * @param roles 角色列表
     * @return 菜单集合
     */
    List<Menu> listMenuByRoles(List<Role> roles);
}
