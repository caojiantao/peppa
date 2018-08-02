package com.cjt.service.system.security.impl;

import com.caojiantao.common.util.CollectionUtils;
import com.cjt.dao.system.security.IRoleDAO;
import com.cjt.dao.system.security.IRoleMenuDAO;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.system.security.RoleDO;
import com.cjt.entity.model.system.security.RoleMenuDO;
import com.cjt.service.system.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleDAO roleDAO;

    private final IRoleMenuDAO roleMenusDAO;

    @Autowired
    public RoleServiceImpl(IRoleDAO roleDAO, IRoleMenuDAO roleMenusDAO) {
        this.roleDAO = roleDAO;
        this.roleMenusDAO = roleMenusDAO;
    }

    @Override
    public List<RoleDO> getRolesByUserId(int userId) {
        return roleDAO.getRolesByUserId(userId);
    }

    @Override
    public List<RoleDO> listRoles(RoleDTO roleDTO) {
        return roleDAO.listObjects(roleDTO);
    }

    @Override
    public int countRoles(RoleDTO dto) {
        return roleDAO.countObjects(dto);
    }

    @Override
    public RoleDO getRoleById(int id) {
        return roleDAO.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(RoleDO role, List<Integer> menuIds) {
        LocalDateTime now = LocalDateTime.now();
        // 简单处理，先删除全部关联数据，后进行整体插入
        roleMenusDAO.deleteByRoleId(role.getId());
        if (role.getId() == null) {
            role.setGmtCreate(now);
            roleDAO.insert(role);
            saveRoleMenus(role.getId(), menuIds, now);
            return role.getId() > 0;
        } else {
            role.setGmtModified(now);
            saveRoleMenus(role.getId(), menuIds, now);
            return roleDAO.updateById(role) > 0;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRoleById(int id) {
        roleMenusDAO.deleteByRoleId(id);
        return roleDAO.deleteById(id) > 0;
    }

    private void saveRoleMenus(int roleId, List<Integer> menuIds, LocalDateTime now) {
        if (CollectionUtils.isNotEmpty(menuIds)) {
            List<RoleMenuDO> roleMenus = Collections.emptyList();
            menuIds.forEach(menuId -> {
                RoleMenuDO roleMenu = new RoleMenuDO();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenu.setGmtCreate(now);
                roleMenus.add(roleMenu);
            });
            roleMenusDAO.saveRoleMenus(roleMenus);
        }
    }
}
