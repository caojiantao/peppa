package com.cjt.service.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.dto.BasePageDTO;
import com.cjt.entity.admin.security.Role;

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
    JSONObject listRoleByPage(BasePageDTO pageDTO);

    /**
     * 获取指定ID的角色信息
     *
     * @param id 角色ID
     * @return 角色信息
     */
    Role getRoleById(int id);
}
