package com.cjt.entity.vo;

import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.Role;
import com.cjt.entity.model.security.User;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-01-25 23:36:55
 */
public class UserVo extends User{

    private List<Menu> menus;

    private List<Role> roles;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
