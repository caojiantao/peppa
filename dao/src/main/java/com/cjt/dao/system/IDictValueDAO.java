package com.cjt.dao.system;

import com.cjt.dao.IBaseDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.DictValue;

/**
 * @author caojiantao
 */
public interface IDictValueDAO extends IBaseDAO<DictValue, DictionaryDTO> {

    int deleteDictValueBySetId(int setId);
}
