package com.cjt.service.security.impl;

import com.cjt.dao.security.IMenuDAO;
import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.Role;
import com.cjt.entity.model.security.User;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class MenuServiceImpl implements IMenuService {

    private final IMenuDAO menuDAO;

    private final IUserService userService;

    private final IRoleService roleService;

    @Autowired
    public MenuServiceImpl(IMenuDAO menuDAO, IUserService userService, IRoleService roleService) {
        this.menuDAO = menuDAO;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public List<Menu> getMenus() {
        return menuDAO.getMenusByRoleIds(null);
    }

    @Override
    public List<Menu> getMenusByRoleId(int roleId) {
        return menuDAO.getMenusByRoleIds(Collections.singletonList(roleId));
    }

    @Override
    public List<Menu> getMenusByUserId(int userId) {
        User user = userService.getUserById(userId);
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<Integer> roleIds = new ArrayList<>();
        roles.forEach(role -> roleIds.add(role.getId()));
        return menuDAO.getMenusByRoleIds(roleIds);
    }

    @Override
    public Menu getMenuById(int id) {
        return menuDAO.getById(id);
    }

    @Override
    public boolean saveMenu(Menu menu) {
        if (menu.getId() == null) {
            menuDAO.insert(menu);
            return menu.getId() > 0;
        } else {
            return menuDAO.updateById(menu) > 0;
        }
    }

    @Override
    public boolean deleteMenuById(int id) {
        return menuDAO.deleteById(id) > 0;
    }
}
