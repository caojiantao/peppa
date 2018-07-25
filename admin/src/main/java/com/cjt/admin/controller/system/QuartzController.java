package com.cjt.admin.controller.system;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.model.system.Quartz;
import com.cjt.quartz.IQuartzService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController extends BaseController {

    private final IQuartzService quartzService;

    @Autowired
    public QuartzController(IQuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @GetMapping("")
    public Object listQuartz(QuartzDTO dto) {
        return quartzService.listQuartz(dto);
    }

    @GetMapping("/{id}")
    public Object getQuartzById(@PathVariable("id") int id) {
        return quartzService.getQuartzById(id);
    }

    @PostMapping("")
    public Object saveQuartz(Quartz quartz) {
        if (CronExpression.isValidExpression(quartz.getCronExpre())) {
            if (quartzService.saveQuartz(quartz)) {
                return success("操作成功", quartz);
            } else {
                return failure("操作失败请重试");
            }
        } else {
            return failure("时间表达式不合法");
        }
    }

    @PutMapping("")
    public Object updateQuartz(Quartz quartz) {
        if (CronExpression.isValidExpression(quartz.getCronExpre())) {
            if (quartzService.updateQuartz(quartz)) {
                return success("操作成功", quartz);
            } else {
                return failure("操作失败请重试");
            }
        } else {
            return failure("时间表达式不合法");
        }
    }

    @DeleteMapping("/{id}")
    public Object removeQuartz(@PathVariable("id") int id) {
        return quartzService.removeQuartzById(id) ? success("操作成功") : failure("操作失败请重试");
    }

    @PostMapping("/executeQuartzById")
    public ResultDTO executeQuartzById(int id) {
        return quartzService.executeQuartzById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
