package com.cjt.entity.model.system.security;

import com.cjt.entity.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDO extends BaseDO {

    private String name;
    private Integer parentId;
    private String href;
    private String iconClass;
    private Integer order;
}
