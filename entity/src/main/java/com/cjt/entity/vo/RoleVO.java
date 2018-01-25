package com.cjt.entity.vo;

import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.Role;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-01-25 23:18:28
 */
public class RoleVO extends Role {

    private List<Menu> menus;

    @Override
    public List<Menu> getMenus() {
        return menus;
    }

    @Override
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
