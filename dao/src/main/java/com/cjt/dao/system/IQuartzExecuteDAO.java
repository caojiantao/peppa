package com.cjt.dao.system;

/**
 * @author caojiantao
 */
public interface IQuartzExecuteDAO {

    void saveExecute(String jobClass);

    int removeExecuteByJobClass(String jobClass);
}
