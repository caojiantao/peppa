package com.cjt.service.security;


import com.cjt.entity.model.security.Menu;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuService {

    Menu getMenuById(int id);

    List<Menu> getMenus();

    List<Menu> getMenusByUserId(int userId);

    List<Menu> getMenusByRoleId(int roleId);

    boolean saveMenu(Menu menu);

    boolean deleteMenuById(int id);
}
