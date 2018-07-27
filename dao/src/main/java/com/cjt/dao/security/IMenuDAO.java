package com.cjt.dao.security;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.model.security.Menu;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuDAO extends IBaseDAO<Menu, Object>{

    /**
     * 根据角色列表，获取菜单集合（去重）
     *
     * @param roleIds 角色ID结合
     * @return 菜单集合
     */
    List<Menu> listMenuByRoleIds(List<Integer> roleIds);
}
