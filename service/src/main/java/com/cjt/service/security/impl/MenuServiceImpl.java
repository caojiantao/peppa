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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuDAO menuDAO;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Menu> listMenu() {
        return menuDAO.listMenuByRoleIds(null);
    }

    @Override
    public List<Menu> listMenuByRoleId(int roleId) {
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        return menuDAO.listMenuByRoleIds(roleIds);
    }

    @Override
    public List<Menu> listMenuByUserId(Long userId) {
        User user = userService.getUserByUserId(userId);
        List<Role> roles = roleService.listRoleByUserId(user.getId());
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        if (roleIds.isEmpty()){
            return null;
        }
        return menuDAO.listMenuByRoleIds(roleIds);
    }

    @Override
    public Menu getMenuById(int id) {
        return menuDAO.getMenuById(id);
    }

    @Override
    public boolean saveMenu(Menu menu) {
        menuDAO.saveMenu(menu);
        return menu.getId() > 0;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return menuDAO.updateMenu(menu) > 0;
    }

    @Override
    public boolean removeMenuById(int id) {
        return menuDAO.removeMenuById(id) > 0;
    }

}
