package com.cjt.service.impl;

import com.cjt.dao.admin.security.IMenuDAO;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.Role;
import com.cjt.service.IMenuService;
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
    private IMenuDAO menuDao;

    @Override
    public List<Menu> listMenuByRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        // 获取DB原始数据
        List<Menu> menus = menuDao.listMenuByRoles(roles);
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
