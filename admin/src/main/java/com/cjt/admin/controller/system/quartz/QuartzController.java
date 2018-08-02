package com.cjt.admin.controller.system.quartz;

import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.quartz.QuartzDO;
import com.cjt.quartz.IQuartzService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/quartz/job")
public class QuartzController extends BaseController {

    private final IQuartzService quartzService;

    @Autowired
    public QuartzController(IQuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @GetMapping("/getQuartzPageData")
    public ResultDTO getQuartzPageData(QuartzDTO dto) {
        List<QuartzDO> quartzs = quartzService.listQuartz(dto);
        int total = quartzService.countQuartz(dto);
        return success(JsonUtils.toPageData(quartzs, total));
    }

    @GetMapping("/getQuartzById")
    public ResultDTO getQuartzById(int id) {
        return success(quartzService.getQuartzById(id));
    }

    @PostMapping("/saveQuartz")
    public ResultDTO saveQuartz(QuartzDO quartzDO) {
        if (!CronExpression.isValidExpression(quartzDO.getCronExpression())) {
            return failure("时间表达式不合法");
        }
        return quartzService.saveQuartz(quartzDO) ? success("操作成功", quartzDO) : failure("操作失败请重试");
    }

    @DeleteMapping("/deleteQuartzById")
    public ResultDTO deleteQuartzById(int id) {
        return quartzService.deleteQuartzById(id) ? success("操作成功") : failure("操作失败请重试");
    }

    @PostMapping("/executeQuartzById")
    public ResultDTO executeQuartzById(int id) {
        return quartzService.executeQuartzById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
