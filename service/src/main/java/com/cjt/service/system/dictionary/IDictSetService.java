package com.cjt.service.system.dictionary;

import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.dictionary.DictSetDO;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IDictSetService {

    /**
     * 新增字典集
     *
     * @param set 字典集
     * @return 操作成功与否
     */
    boolean saveDictSet(DictSetDO set);

    /**
     * 删除指定字典集
     *
     * @param id 字典集ID
     * @return 操作成功与否
     */
    boolean removeDictSetById(int id);

    /**
     * 获取指定字典集
     *
     * @param id 字典集ID
     * @return 字典集
     */
    DictSetDO getDictSetById(int id);

    /**
     * 获取指定条件下的字典集集合
     *
     * @param dto 条件
     * @return 字典集集合
     */
    List<DictSetDO> listDictSet(DictionaryDTO dto);

    /**
     * 获取指定条件下的字典集集合大小
     *
     * @param dto 条件
     * @return 字典集集合大小
     */
    int countDictSet(DictionaryDTO dto);
}
