package com.cjt.service.system.impl;

import com.cjt.dao.system.IDictionaryDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;
import com.cjt.service.system.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService {

    private final IDictionaryDAO dictionaryDAO;

    @Autowired
    public DictionaryServiceImpl(IDictionaryDAO dictionaryDAO) {
        this.dictionaryDAO = dictionaryDAO;
    }

    @Override
    public boolean saveDictSet(DictSet set) {
        dictionaryDAO.saveDictSet(set);
        return set.getId() != null;
    }

    @Override
    public boolean removeDictSetById(int id) {
        return dictionaryDAO.removeDictSetById(id) > 0;
    }

    @Override
    public boolean updateDictSet(DictSet set) {
        return dictionaryDAO.updateDictSet(set) > 0;
    }

    @Override
    public DictSet getDictSetById(int id) {
        return dictionaryDAO.getDictSetById(id);
    }

    @Override
    public List<DictSet> listDictSet(DictionaryDTO dto) {
        return dictionaryDAO.listDictSet(dto);
    }

    @Override
    public int countDictSet(DictionaryDTO dto) {
        return dictionaryDAO.countDictSet(dto);
    }

    @Override
    public boolean saveDictValue(DictValue value) {
        dictionaryDAO.saveDictValue(value);
        return value.getId() != null;
    }

    @Override
    public boolean removeDictValueById(int id) {
        return dictionaryDAO.removeDictValueById(id) > 0;
    }

    @Override
    public boolean updateDictValue(DictValue value) {
        return dictionaryDAO.updateDictValue(value) > 0;
    }

    @Override
    public DictValue getDictValueById(int id) {
        return dictionaryDAO.getDictValueById(id);
    }

    @Override
    public List<DictValue> listDictValue(DictionaryDTO dto) {
        return dictionaryDAO.listDictValue(dto);
    }

    @Override
    public int countDictValue(DictionaryDTO dto) {
        return dictionaryDAO.countDictValue(dto);
    }
}
