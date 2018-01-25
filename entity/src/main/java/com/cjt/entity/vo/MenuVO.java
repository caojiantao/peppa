package com.cjt.entity.vo;

import com.cjt.entity.model.security.Menu;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-01-25 23:34:16
 */
public class MenuVO extends Menu {

    private List<MenuVO> children;

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }
}
