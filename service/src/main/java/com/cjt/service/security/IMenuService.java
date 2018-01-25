package com.cjt.service.security;


import com.cjt.entity.security.Menu;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuService {

    /**
     * 获取用户对应菜单集合
     *
     * @param userId 用户ID
     * @return 菜单集合
     */
    List<Menu> listMenuByUserId(Long userId);

    /**
     * 获取所有菜单集合
     *
     * @return 菜单集合
     */
    List<Menu> listMenu();

    /**
     * 获取指定ID的菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    Menu getMenuById(int id);

    /**
     * 新增菜单
     *
     * @param menu 菜单
     * @return 成功与否
     */
    boolean saveMenu(Menu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 修改的菜单信息
     * @return 成功与否
     */
    boolean updateMenu(Menu menu);

    /**
     * 删除指定ID的菜单
     *
     * @param id 菜单ID
     * @return 成功与否
     */
    boolean removeMenuById(int id);
}
