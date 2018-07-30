package com.cjt.dao;

import java.util.List;

/**
 * DAO查询基类，简化基础编码
 *
 * @author caojiantao
 */
public interface IBaseDAO<T, Q> {

    /**
     * 插入数据
     *
     * @param data 数据对象
     */
    void insert(T data);

    /**
     * 删除指定ID的数据
     *
     * @param id ID
     * @return 影响行数
     */
    int deleteById(int id);

    /**
     * 更新数据
     *
     * @param data 数据对象
     * @return 影响行数
     */
    int updateById(T data);

    /**
     * 获取指定ID的数据
     *
     * @param id ID
     * @return 数据对象
     */
    T getById(int id);

    /**
     * 获取指定查询条件的数据对象集合
     *
     * @param q 查询条件
     * @return 数据对象集合
     */
    List<T> getDatas(Q q);

    /**
     * 获取指定查询条件的数据对象集合大小
     *
     * @param q 查询条件
     * @return 数据对象集合大小
     */
    int getDatasTotal(Q q);
}
