package com.cjt.dao.security;

import com.cjt.entity.security.Menu;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuDAO {

    /**
     * 根据角色列表，获取菜单集合（去重）
     *
     * @param roleIds 角色ID结合
     * @return 菜单集合
     */
    List<Menu> listMenuByRoleIds(List<Integer> roleIds);

    /**
     * 获取所有菜单集合
     *
     * @return 菜单集合
     */
    List<Menu> listAllMenu();

    /**
     * 获取指定ID的菜单
     *
     * @param id 菜单ID
     * @return 菜单
     */
    Menu getMenuById(int id);

    /**
     * 新增菜单
     *
     * @param menu 菜单
     */
    void saveMenu(Menu menu);

    /**
     * 更新菜单信息
     *
     * @param menu 菜单
     * @return 影响行数
     */
    int updateMenu(Menu menu);

    /**
     * 删除指定ID的菜单
     *
     * @param id 菜单ID
     * @return 影响行数
     */
    int removeMenuById(int id);
}
