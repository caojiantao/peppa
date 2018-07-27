package com.cjt.service.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleService {

    /**
     * 根据username获取角色集合
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> listRoleByUserId(long userId);

    /**
     * 分页获取所有角色信息
     *
     * @param pageDTO 分页参数
     * @return 所有角色集合
     */
    JSONObject listRoleByPage(RoleDTO pageDTO);

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
     * @param role    角色实体
     * @return 成功与否
     */
    boolean saveRole(Role role, List<Integer> menuIds);

    /**
     * 删除指定ID的角色
     *
     * @param id 角色ID
     * @return 成功与否
     */
    boolean removeRole(int id);
}
