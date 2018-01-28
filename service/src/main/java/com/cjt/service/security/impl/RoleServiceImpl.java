package com.cjt.service.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.security.IRoleDAO;
import com.cjt.dao.security.IRoleMenusDAO;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;
import com.cjt.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDAO roleDAO;

    @Autowired
    private IRoleMenusDAO roleMenusDAO;

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
        return roleDAO.getRoleById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(Role role, List<Integer> menuIds) {
        roleDAO.saveRole(role);
        roleMenusDAO.saveRoleMenus(role.getId(), menuIds);
        return role.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRole(Role role, List<Integer> menuIds) {
        roleMenusDAO.removeRoleMenus(role.getId());
        roleMenusDAO.saveRoleMenus(role.getId(), menuIds);
        return roleDAO.updateRole(role) > 0;
    }

    @Override
    public boolean removeRole(int id) {
        roleMenusDAO.removeRoleMenus(id);
        return roleDAO.removeRoleById(id) > 0;
    }
}
