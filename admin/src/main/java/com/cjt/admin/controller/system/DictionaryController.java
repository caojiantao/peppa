package com.cjt.admin.controller.system;

import com.alibaba.fastjson.JSONObject;
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
    public JSONObject listDictSetByPage(DictionaryDTO dto) {
        List<DictSet> dictSets = dictionaryService.listDictSet(dto);
        int total = dictionaryService.countDictSet(dto);
        return JsonUtils.toPageData(dictSets, total);
    }

    @GetMapping("/getDictSetById")
    public DictSet getDictSetById(int id) {
        return dictionaryService.getDictSetById(id);
    }

    @PostMapping("/saveDictSet")
    public ResultDTO saveDictSet(DictSet set) {
        return dictionaryService.saveDictSet(set) ? success("保存字典集成功") : failure("保存字典集失败");
    }

    @PostMapping("/updateDictSet")
    public ResultDTO updateDictSet(DictSet set) {
        return dictionaryService.updateDictSet(set) ? success("更新字典集成功") : failure("更新字典集失败");
    }

    @PostMapping("/removeDictSetById")
    public ResultDTO removeDictSetById(int id) {
        return dictionaryService.removeDictSetById(id) ? success("删除字典集成功") : failure("删除字典集失败");
    }

    @GetMapping("/listDictValueByPage")
    public JSONObject listDictValueByPage(DictionaryDTO dto) {
        List<DictValue> dictValues = dictionaryService.listDictValue(dto);
        int total = dictionaryService.countDictValue(dto);
        return JsonUtils.toPageData(dictValues, total);
    }

    @GetMapping("/getDictValueById")
    public DictValue getDictValueById(int id) {
        return dictionaryService.getDictValueById(id);
    }

    @PostMapping("/saveDictValue")
    public ResultDTO saveDictValue(DictValue value) {
        return dictionaryService.saveDictValue(value) ? success("保存字典值成功") : failure("保存字典值失败");
    }

    @PostMapping("/updateDictValue")
    public ResultDTO updateDictValue(DictValue value) {
        return dictionaryService.updateDictValue(value) ? success("更新字典值成功") : failure("更新字典值失败");
    }

    @PostMapping("/removeDictValueById")
    public ResultDTO removeDictValueById(int id) {
        return dictionaryService.removeDictValueById(id) ? success("删除字典值成功") : failure("删除字典值失败");
    }

    @RequestMapping("/listDictSetOpt")
    public Object listDictSetOpt() {
        return dictionaryService.listDictSet(null);
    }
}
