package com.cjt.dao.system.security;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.system.security.RoleDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleDAO extends IBaseDAO<RoleDO, RoleDTO> {

    /**
     * 根据username获取角色集合
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<RoleDO> getRolesByUserId(long userId);
}
