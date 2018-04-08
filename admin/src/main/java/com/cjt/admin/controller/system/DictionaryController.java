package com.cjt.admin.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.controller.BaseController;
import com.cjt.common.util.JsonUtils;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.DictSet;
import com.cjt.entity.model.system.DictValue;
import com.cjt.service.system.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/dictionaries")
public class DictionaryController extends BaseController {

    private final IDictionaryService dictionaryService;

    @Autowired
    public DictionaryController(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/listDictSetByPage")
    public JSONObject listDictSetByPage(@RequestParam Map<String, Object> map) {
        List<DictSet> dictSets = dictionaryService.listDictSet(map);
        int total = dictionaryService.countDictSet(map);
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
    public JSONObject listDictValueByPage(@RequestParam Map<String, Object> map) {
        List<DictValue> dictValues = dictionaryService.listDictValue(map);
        int total = dictionaryService.countDictValue(map);
        return JsonUtils.toPageData(dictValues, total);
    }
}
