package com.cjt.dao.system.dictionary;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.dictionary.DictValueDO;

/**
 * @author caojiantao
 */
public interface IDictValueDAO extends IBaseDAO<DictValueDO, DictionaryDTO> {

    int deleteDictValueBySetId(int setId);
}
