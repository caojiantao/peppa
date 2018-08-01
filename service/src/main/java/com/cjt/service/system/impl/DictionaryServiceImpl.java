package com.cjt.service.system.impl;

import com.cjt.dao.system.IDictSetDAO;
import com.cjt.dao.system.IDictValueDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;
import com.cjt.service.system.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService {

    private final IDictSetDAO dictSetDAO;

    private final IDictValueDAO dictValueDAO;

    @Autowired
    public DictionaryServiceImpl(IDictSetDAO dictSetDAO, IDictValueDAO dictValueDAO) {
        this.dictSetDAO = dictSetDAO;
        this.dictValueDAO = dictValueDAO;
    }

    @Override
    public boolean saveDictSet(DictSet set) {
        if (set.getId() == null){
            dictSetDAO.insert(set);
            return set.getId() != null;
        } else {
            return dictSetDAO.updateById(set) > 0;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeDictSetById(int id) {
        dictValueDAO.deleteDictValueBySetId(id);
        return dictSetDAO.deleteById(id) > 0;
    }

    @Override
    public DictSet getDictSetById(int id) {
        return dictSetDAO.getById(id);
    }

    @Override
    public List<DictSet> listDictSet(DictionaryDTO dto) {
        return dictSetDAO.getDatas(dto);
    }

    @Override
    public int countDictSet(DictionaryDTO dto) {
        return dictSetDAO.getDatasTotal(dto);
    }

    @Override
    public boolean saveDictValue(DictValue value) {
        if (value.getId() == null){
            dictValueDAO.insert(value);
            return value.getId() != null;
        } else {
            return dictValueDAO.updateById(value) > 0;
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
    public DictValue getDictValueById(int id) {
        return dictValueDAO.getById(id);
    }

    @Override
    public List<DictValue> listDictValue(DictionaryDTO dto) {
        return dictValueDAO.getDatas(dto);
    }

    @Override
    public int countDictValue(DictionaryDTO dto) {
        return dictValueDAO.getDatasTotal(dto);
    }
}
