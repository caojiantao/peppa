package com.cjt.service.system.security;

import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.system.security.RoleDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleService {

    List<RoleDO> getRolesByUserId(int userId);

    List<RoleDO> listRoles(RoleDTO dto);

    int countRoles(RoleDTO dto);

    RoleDO getRoleById(int id);

    boolean saveRole(RoleDO role, List<Integer> menuIds);

    boolean deleteRoleById(int id);
}
