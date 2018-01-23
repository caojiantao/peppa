package com.cjt.service;


import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.Role;
import com.cjt.entity.admin.security.User;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuService {

    /**
     * 获取用户对应菜单集合
     *
     * @param user 用户
     * @return 菜单集合
     */
    List<Menu> listMenuByUser(User user);

    /**
     * 获取所有菜单集合
     *
     * @return 菜单集合
     */
    List<Menu> listAllMenu();
}
