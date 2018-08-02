package com.cjt.dao.system.security;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.model.system.security.MenuDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IMenuDAO extends IBaseDAO<MenuDO, Object>{

    /**
     * 根据角色列表，获取菜单集合（去重）
     *
     * @param roleIds 角色ID结合
     * @return 菜单集合
     */
    List<MenuDO> getMenusByRoleIds(List<Integer> roleIds);
}
