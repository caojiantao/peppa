package com.cjt.backend.entity;

import java.util.List;

/**
 * @author caojiantao
 * 菜单实体类
 */
public class Menu {

  private Integer id;

  private String name;

  private Integer parentId;

  private String href;

  private String iconClass;

  private List<Menu> children;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public List<Menu> getChildren() {
    return children;
  }

  public void setChildren(List<Menu> children) {
    this.children = children;
  }

  public String getIconClass() {
    return iconClass;
  }

  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }
}
