package com.cjt.service.system.dictionary;

import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.dictionary.DictValueDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IDictValueService {

    /**
     * 新增字典值
     *
     * @param set 字典值
     * @return 操作成功与否
     */
    boolean saveDictValue(DictValueDO set);

    /**
     * 删除指定字典值
     *
     * @param id 字典值ID
     * @return 操作成功与否
     */
    boolean removeDictValueById(int id);

    /**
     * 删除指定字典值
     *
     * @param setId 字典集ID
     * @return 操作成功与否
     */
    boolean removeDictValueBySetId(int setId);

    /**
     * 获取指定字典值
     *
     * @param id 字典值ID
     * @return 字典值
     */
    DictValueDO getDictValueById(int id);

    /**
     * 获取指定条件下的字典值集合
     *
     * @param dto 条件
     * @return 字典值集合
     */
    List<DictValueDO> listDictValue(DictionaryDTO dto);

    /**
     * 获取指定条件下的字典值集合大小
     *
     * @param dto 条件
     * @return 字典值集合大小
     */
    int countDictValue(DictionaryDTO dto);
}
