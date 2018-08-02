package com.cjt.service.system.dictionary.impl;

import com.cjt.dao.system.dictionary.IDictValueDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.dictionary.DictValueDO;
import com.cjt.service.system.dictionary.IDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class DictValueServiceImpl implements IDictValueService {

    private final IDictValueDAO dictValueDAO;

    @Autowired
    public DictValueServiceImpl(IDictValueDAO dictValueDAO) {
        this.dictValueDAO = dictValueDAO;
    }

    @Override
    public boolean saveDictValue(DictValueDO dictValue) {
        LocalDateTime now = LocalDateTime.now();
        if (dictValue.getId() == null) {
            dictValue.setGmtCreate(now);
            dictValueDAO.insert(dictValue);
            return dictValue.getId() != null;
        } else {
            dictValue.setGmtModified(now);
            return dictValueDAO.updateById(dictValue) > 0;
        }
    }

    @Override
    public boolean removeDictValueById(int id) {
        return dictValueDAO.deleteById(id) > 0;
    }

    @Override
    public boolean removeDictValueBySetId(int setId) {
        return dictValueDAO.deleteDictValueBySetId(setId) > 0;
    }

    @Override
    public DictValueDO getDictValueById(int id) {
        return dictValueDAO.getById(id);
    }

    @Override
    public List<DictValueDO> listDictValue(DictionaryDTO dto) {
        return dictValueDAO.listObjects(dto);
    }

    @Override
    public int countDictValue(DictionaryDTO dto) {
        return dictValueDAO.countObjects(dto);
    }
}
