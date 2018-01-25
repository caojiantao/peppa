package com.cjt.service.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.dto.RoleDTO;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.security.IMenuDAO;
import com.cjt.dao.security.IRoleDAO;
import com.cjt.entity.security.Menu;
import com.cjt.entity.security.Role;
import com.cjt.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDAO roleDAO;

    @Autowired
    private IMenuDAO menuDAO;

    @Override
    public List<Role> listRoleByUserId(long userId) {
        return roleDAO.listRoleByUserId(userId);
    }

    @Override
    public JSONObject listRoleByPage(RoleDTO roleDTO) {
        List<Role> roles = roleDAO.listRole(roleDTO);
        int total = roleDAO.countRole();
        return JsonUtils.toPageData(roles, total);
    }

    @Override
    public Role getRoleById(int id) {
        Role role = roleDAO.getRoleById(id);
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(id);
        List<Menu> menus = menuDAO.listMenuByRoleIds(roleIds);
        role.setMenus(menus);
        return role;
    }
}
