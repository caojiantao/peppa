package com.cjt.entity.dto;

/**
 * @author caojiantao
 */
public class RoleDTO extends BasePageDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
