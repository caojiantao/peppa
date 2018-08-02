package com.cjt.service.system.security.impl;

import com.cjt.dao.system.security.IMenuDAO;
import com.cjt.entity.model.system.security.MenuDO;
import com.cjt.entity.model.system.security.RoleDO;
import com.cjt.entity.model.system.security.UserDO;
import com.cjt.service.system.security.IMenuService;
import com.cjt.service.system.security.IRoleService;
import com.cjt.service.system.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<MenuDO> listMenus() {
        return menuDAO.getMenusByRoleIds(null);
    }

    @Override
    public List<MenuDO> getMenusByRoleId(int roleId) {
        return menuDAO.getMenusByRoleIds(Collections.singletonList(roleId));
    }

    @Override
    public List<MenuDO> getMenusByUserId(int userId) {
        UserDO user = userService.getUserById(userId);
        List<RoleDO> roles = roleService.getRolesByUserId(user.getId());
        List<Integer> roleIds = new ArrayList<>();
        roles.forEach(role -> roleIds.add(role.getId()));
        return menuDAO.getMenusByRoleIds(roleIds);
    }

    @Override
    public MenuDO getMenuById(int id) {
        return menuDAO.getById(id);
    }

    @Override
    public boolean saveMenu(MenuDO menu) {
        if (menu.getId() == null) {
            menu.setGmtCreate(LocalDateTime.now());
            menuDAO.insert(menu);
            return menu.getId() > 0;
        } else {
            menu.setGmtModified(LocalDateTime.now());
            return menuDAO.updateById(menu) > 0;
        }
    }

    @Override
    public boolean deleteMenuById(int id) {
        return menuDAO.deleteById(id) > 0;
    }
}
