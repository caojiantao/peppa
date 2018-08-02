package com.cjt.admin.controller.system.dictionary;

import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.dictionary.DictSetDO;
import com.cjt.service.system.dictionary.IDictSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/dictionary/dictSet")
public class DictSetController extends BaseController {

    private final IDictSetService dictSetService;

    @Autowired
    public DictSetController(IDictSetService dictSetService) {
        this.dictSetService = dictSetService;
    }

    @GetMapping("/listDictSetPageData")
    public ResultDTO listDictSetPageData(DictionaryDTO dto) {
        List<DictSetDO> dictSets = dictSetService.listDictSet(dto);
        int total = dictSetService.countDictSet(dto);
        return success(JsonUtils.toPageData(dictSets, total));
    }

    @GetMapping("/getDictSetById")
    public ResultDTO getDictSetById(int id) {
        return success(dictSetService.getDictSetById(id));
    }

    @PostMapping("/saveDictSet")
    public ResultDTO saveDictSet(DictSetDO set) {
        return dictSetService.saveDictSet(set) ? success("操作成功", set) : failure("操作失败请重试");
    }

    @PostMapping("/removeDictSetById")
    public ResultDTO removeDictSetById(int id) {
        return dictSetService.removeDictSetById(id) ? success("操作成功") : failure("操作失败请重试");
    }

    @RequestMapping("/listDictSetOpt")
    public ResultDTO listDictSetOpt() {
        return success(dictSetService.listDictSet(null));
    }
}
