package com.cjt.dao.security;

import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleDAO {

    /**
     * 根据username获取角色集合
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> listRoleByUserId(long userId);

    /**
     * 分页获取角色
     *
     * @param dto 分页参数
     * @return 角色集合
     */
    List<Role> listRole(RoleDTO dto);

    /**
     * 得到所有角色数量
     *
     * @return 所有角色数量
     */
    int countRole();

    /**
     * 获取指定ID的角色信息
     *
     * @param id 角色ID
     * @return 角色信息
     */
    Role getRoleById(int id);

    /**
     * 新增角色
     *
     * @param role 角色
     */
    void saveRole(Role role);

    /**
     * 删除指定ID的角色
     *
     * @param id 角色ID
     * @return 影响行数
     */
    int removeRoleById(int id);

    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return 影响行数
     */
    int updateRole(Role role);
}
