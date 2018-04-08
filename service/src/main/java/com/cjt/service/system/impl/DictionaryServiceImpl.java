package com.cjt.service.system.impl;

import com.cjt.dao.system.IDictionaryDAO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;
import com.cjt.service.system.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<DictSet> listDictSet(Map<String, Object> map) {
        return dictionaryDAO.listDictSet(map);
    }

    @Override
    public int countDictSet(Map<String, Object> map) {
        return dictionaryDAO.countDictSet(map);
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
    public List<DictValue> listDictValue(Map<String, Object> map) {
        return dictionaryDAO.listDictValue(map);
    }

    @Override
    public int countDictValue(Map<String, Object> map) {
        return dictionaryDAO.countDictValue(map);
    }
}
