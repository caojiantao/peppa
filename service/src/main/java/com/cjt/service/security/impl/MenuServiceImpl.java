package com.cjt.service.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.dao.security.IMenuDAO;
import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.Role;
import com.cjt.entity.model.security.User;
import com.cjt.entity.vo.MenuVO;
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
    public List<MenuVO> listMenuVOByUserId(Long userId) {
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
    public List<MenuVO> listMenuVO() {
        List<Menu> menus = menuDAO.listMenu();
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
    private List<MenuVO> formatMenuList(List<Menu> menus) {
        // 采用spring自带工具类，实质就是实现Map<V, List<V>>，得到以parentId为分组的map
        MultiValueMap<Integer, MenuVO> map = new LinkedMultiValueMap<>();
        for (Menu menu : menus) {
            // 直接强转会报错，借用JSON的小技巧
            map.add(menu.getParentId(), JSONObject.parseObject(JSONObject.toJSONString(menu), MenuVO.class));
        }
        return setSubItem(map, 0);
    }

    private List<MenuVO> setSubItem(MultiValueMap<Integer, MenuVO> map, int parentId) {
        List<MenuVO> result = new ArrayList<>();
        if (map.containsKey(parentId)) {
            List<MenuVO> menus = map.get(parentId);
            for (MenuVO menuVO : menus) {
                // 递归设置子项
                menuVO.setChildren(setSubItem(map, menuVO.getId()));
                // 注意添加到当前同级兄弟list中
                result.add(menuVO);
            }
        }
        return result;
    }
}
