package com.cjt.dao.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author caojiantao
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private ThreadLocal<String> dataSourceKeyLocal = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKeyLocal.get();
    }

    public void setDataSourceKey(String key) {
        dataSourceKeyLocal.set(key);
    }

    public void clearDataSourceKey() {
        dataSourceKeyLocal.remove();
    }
}
