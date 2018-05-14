package com.cjt.dao.db;

/**
 * @author caojiantao
 */

public enum DataSourceEnum {

    /**
     * 主库
     */
    MASTER("master"),

    /**
     * 从库
     */
    SLAVE("slave");

    private String key;

    DataSourceEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
