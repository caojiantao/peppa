package com.cjt.dao.system;

import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IDictionaryDAO {

    /* ---------------------------- 字典集操作 ---------------------------- */

    /**
     * 新增字典集
     *
     * @param set 字典集
     */
    void saveDictSet(DictSet set);

    /**
     * 删除指定字典集
     *
     * @param id 字典集ID
     * @return 影响行数
     */
    int removeDictSetById(int id);

    /**
     * 更新字典集
     *
     * @param set 字典集
     * @return 影响行数
     */
    int updateDictSet(DictSet set);

    /**
     * 获取指定字典集
     *
     * @param id 字典集ID
     * @return 字典集
     */
    DictSet getDictSetById(int id);

    /**
     * 获取指定条件下的字典集集合
     *
     * @param dto 条件
     * @return 字典集集合
     */
    List<DictSet> listDictSet(DictionaryDTO dto);

    /**
     * 获取指定条件下的字典集集合大小
     *
     * @param dto 条件
     * @return 字典集集合大小
     */
    int countDictSet(DictionaryDTO dto);

    /* ---------------------------- 字典值操作 ---------------------------- */

    /**
     * 新增字典值
     *
     * @param set 字典值
     */
    void saveDictValue(DictValue set);

    /**
     * 删除指定字典值
     *
     * @param id 字典值ID
     * @return 影响行数
     */
    int removeDictValueById(int id);

    /**
     * 删除指定字典值
     *
     * @param setId 字典集ID
     * @return 影响行数
     */
    int removeDictValueBySetId(int setId);

    /**
     * 更新字典值
     *
     * @param set 字典值
     * @return 影响行数
     */
    int updateDictValue(DictValue set);

    /**
     * 获取指定字典值
     *
     * @param id 字典值ID
     * @return 字典值
     */
    DictValue getDictValueById(int id);

    /**
     * 获取指定条件下的字典值集合
     *
     * @param dto 条件
     * @return 字典值集合
     */
    List<DictValue> listDictValue(DictionaryDTO dto);

    /**
     * 获取指定条件下的字典值集合大小
     *
     * @param dto 条件
     * @return 字典值集合大小
     */
    int countDictValue(DictionaryDTO dto);
}
