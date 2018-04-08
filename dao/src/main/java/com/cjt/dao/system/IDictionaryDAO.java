package com.cjt.dao.system;

import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;

import java.util.List;
import java.util.Map;

/**
 * @author caojiantao
 */
public interface IDictionaryDAO {

    /* ---------------------------- 字典集操作 ---------------------------- */

    /**
     * 新增字典集
     * @param set 字典集
     */
    void saveDictSet(DictSet set);

    /**
     * 删除指定字典集
     * @param id 字典集ID
     * @return 影响行数
     */
    int removeDictSetById(int id);

    /**
     * 更新字典集
     * @param set 字典集
     * @return 影响行数
     */
    int updateDictSet(DictSet set);

    /**
     * 获取指定字典集
     * @param id 字典集ID
     * @return 字典集
     */
    DictSet getDictSetById(int id);

    /**
     * 获取指定条件下的字典集集合
     * @param map 条件
     * @return 字典集集合
     */
    List<DictSet> listDictSet(Map<String, Object> map);

    /**
     * 获取指定条件下的字典集集合大小
     * @param map 条件
     * @return 字典集集合大小
     */
    int countDictSet(Map<String, Object> map);

    /* ---------------------------- 字典值操作 ---------------------------- */

    /**
     * 新增字典值
     * @param set 字典值
     */
    void saveDictValue(DictValue set);

    /**
     * 删除指定字典值
     * @param id 字典值ID
     * @return 影响行数
     */
    int removeDictValueById(int id);

    /**
     * 更新字典值
     * @param set 字典值
     * @return 影响行数
     */
    int updateDictValue(DictValue set);

    /**
     * 获取指定字典值
     * @param id 字典值ID
     * @return 字典值
     */
    DictValue getDictValueById(int id);

    /**
     * 获取指定条件下的字典值集合
     * @param map 条件
     * @return 字典值集合
     */
    List<DictValue> listDictValue(Map<String, Object> map);

    /**
     * 获取指定条件下的字典值集合大小
     * @param map 条件
     * @return 字典值集合大小
     */
    int countDictValue(Map<String, Object> map);
}
