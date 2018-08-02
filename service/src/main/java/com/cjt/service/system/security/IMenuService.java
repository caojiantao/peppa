package com.cjt.service.system.security;


import com.cjt.entity.model.system.security.MenuDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuService {

    MenuDO getMenuById(int id);

    List<MenuDO> listMenus();

    List<MenuDO> getMenusByUserId(int userId);

    List<MenuDO> getMenusByRoleId(int roleId);

    boolean saveMenu(MenuDO menu);

    boolean deleteMenuById(int id);
}
