package com.cjt.service.security;

import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleService {

    List<Role> getRolesByUserId(int userId);

    List<Role> getRoles(RoleDTO dto);

    int getRolesTotal(RoleDTO dto);

    Role getRoleById(int id);

    boolean saveRole(Role role, List<Integer> menuIds);

    boolean deleteRoleById(int id);
}
