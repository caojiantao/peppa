package com.cjt.entity.model.security;

import lombok.Data;

/**
 * 菜单实体类
 *
 * @author caojiantao
 */
@Data
public class Menu {

    private Integer id;
    private String name;
    private Integer parentId;
    private String href;
    private String iconClass;
}
