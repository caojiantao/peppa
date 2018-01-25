package com.cjt.service.security.impl;

import com.cjt.dao.security.IMenuDAO;
import com.cjt.entity.security.Menu;
import com.cjt.entity.security.Role;
import com.cjt.entity.security.User;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    private IMenuDAO menuDAO;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Menu> listMenuByUserId(Long userId) {
        User user = userService.getUserByUserId(userId);
        List<Role> roles = roleService.listRoleByUserId(user.getId());
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        List<Menu> menus = menuDAO.listMenuByRoleIds(roleIds);
        return formatMenuList(menus);
    }

    @Override
    public List<Menu> listMenu() {
        List<Menu> menus = menuDAO.listAllMenu();
        return formatMenuList(menus);
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

    /**
     * 格式化原始 menus，方便前端遍历
     *
     * @param menus 原始数据 menus
     * @return 格式化后的 menus
     */
    private List<Menu> formatMenuList(List<Menu> menus) {
        // 采用spring自带工具类，实质就是实现Map<V, List<V>>，得到以parentId为分组的map
        MultiValueMap<Integer, Menu> map = new LinkedMultiValueMap<>();
        for (Menu menu : menus) {
            map.add(menu.getParentId(), menu);
        }
        return setSubItem(map, 0);
    }

    private List<Menu> setSubItem(MultiValueMap<Integer, Menu> map, int parentId) {
        List<Menu> result = new ArrayList<>();
        if (map.containsKey(parentId)) {
            List<Menu> menus = map.get(parentId);
            for (Menu menu : menus) {
                // 递归设置子项
                menu.setChildren(setSubItem(map, menu.getId()));
                // 注意添加到当前同级兄弟list中
                result.add(menu);
            }
        }
        return result;
    }
}
