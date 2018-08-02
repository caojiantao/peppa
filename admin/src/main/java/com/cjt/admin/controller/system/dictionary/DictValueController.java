package com.cjt.admin.controller.system.dictionary;

import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.DictionaryDTO;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.dictionary.DictValueDO;
import com.cjt.service.system.dictionary.IDictValueService;
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
@RequestMapping("/system/dictionary/dictValue")
public class DictValueController extends BaseController {
    
    private final IDictValueService dictValueService;

    @Autowired
    public DictValueController(IDictValueService dictValueService) {
        this.dictValueService = dictValueService;
    }

    @GetMapping("/listDictValuePageData")
    public ResultDTO listDictValuePageData(DictionaryDTO dto) {
        List<DictValueDO> dictValues = dictValueService.listDictValue(dto);
        int total = dictValueService.countDictValue(dto);
        return success(JsonUtils.toPageData(dictValues, total));
    }

    @GetMapping("/getDictValueById")
    public ResultDTO getDictValueById(int id) {
        return success(dictValueService.getDictValueById(id));
    }

    @PostMapping("/saveDictValue")
    public ResultDTO saveDictValue(DictValueDO value) {
        return dictValueService.saveDictValue(value) ? success("操作成功", value) : failure("操作失败请重试");
    }

    @PostMapping("/removeDictValueById")
    public ResultDTO removeDictValueById(int id) {
        return dictValueService.removeDictValueById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
