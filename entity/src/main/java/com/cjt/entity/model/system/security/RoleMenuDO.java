package com.cjt.entity.model.system.security;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenuDO extends BaseDO{

    private Integer roleId;
    private Integer menuId;
}
