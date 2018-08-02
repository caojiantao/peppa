package com.cjt.service.system.dictionary.impl;

import com.cjt.dao.system.dictionary.IDictSetDAO;
import com.cjt.dao.system.dictionary.IDictValueDAO;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.model.system.dictionary.DictSetDO;
import com.cjt.service.system.dictionary.IDictSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class DictSetServiceImpl implements IDictSetService {

    private final IDictSetDAO dictSetDAO;

    private final IDictValueDAO dictValueDAO;

    @Autowired
    public DictSetServiceImpl(IDictSetDAO dictSetDAO, IDictValueDAO dictValueDAO) {
        this.dictSetDAO = dictSetDAO;
        this.dictValueDAO = dictValueDAO;
    }

    @Override
    public boolean saveDictSet(DictSetDO dictSet) {
        LocalDateTime now = LocalDateTime.now();
        if (dictSet.getId() == null) {
            dictSet.setGmtCreate(now);
            dictSetDAO.insert(dictSet);
            return dictSet.getId() != null;
        } else {
            dictSet.setGmtModified(now);
            return dictSetDAO.updateById(dictSet) > 0;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeDictSetById(int id) {
        dictValueDAO.deleteDictValueBySetId(id);
        return dictSetDAO.deleteById(id) > 0;
    }

    @Override
    public DictSetDO getDictSetById(int id) {
        return dictSetDAO.getById(id);
    }

    @Override
    public List<DictSetDO> listDictSet(DictionaryDTO dto) {
        return dictSetDAO.listObjects(dto);
    }

    @Override
    public int countDictSet(DictionaryDTO dto) {
        return dictSetDAO.countObjects(dto);
    }
}
