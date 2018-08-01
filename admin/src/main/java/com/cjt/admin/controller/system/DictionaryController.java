package com.cjt.admin.controller.system;

import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;
import com.cjt.service.system.IDictionaryService;
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
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

    private final IDictionaryService dictionaryService;

    @Autowired
    public DictionaryController(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/listDictSetByPage")
    public ResultDTO listDictSetByPage(DictionaryDTO dto) {
        List<DictSet> dictSets = dictionaryService.listDictSet(dto);
        int total = dictionaryService.countDictSet(dto);
        return success(JsonUtils.toPageData(dictSets, total));
    }

    @GetMapping("/getDictSetById")
    public ResultDTO getDictSetById(int id) {
        return success(dictionaryService.getDictSetById(id));
    }

    @PostMapping("/saveDictSet")
    public ResultDTO saveDictSet(DictSet set) {
        return dictionaryService.saveDictSet(set) ? success("操作成功", set) : failure("操作失败请重试");
    }

    @PostMapping("/removeDictSetById")
    public ResultDTO removeDictSetById(int id) {
        return dictionaryService.removeDictSetById(id) ? success("操作成功") : failure("操作失败请重试");
    }

    @GetMapping("/listDictValueByPage")
    public ResultDTO listDictValueByPage(DictionaryDTO dto) {
        List<DictValue> dictValues = dictionaryService.listDictValue(dto);
        int total = dictionaryService.countDictValue(dto);
        return success(JsonUtils.toPageData(dictValues, total));
    }

    @GetMapping("/getDictValueById")
    public ResultDTO getDictValueById(int id) {
        return success(dictionaryService.getDictValueById(id));
    }

    @PostMapping("/saveDictValue")
    public ResultDTO saveDictValue(DictValue value) {
        return dictionaryService.saveDictValue(value) ? success("操作成功", value) : failure("操作失败请重试");
    }

    @PostMapping("/removeDictValueById")
    public ResultDTO removeDictValueById(int id) {
        return dictionaryService.removeDictValueById(id) ? success("操作成功") : failure("操作失败请重试");
    }

    @RequestMapping("/listDictSetOpt")
    public ResultDTO listDictSetOpt() {
        return success(dictionaryService.listDictSet(null));
    }
}
