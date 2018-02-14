package com.cjt.admin.controller;

import com.cjt.admin.service.IQuartzService;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.job.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/quartzs")
public class QuartzController extends BaseController {

    @Autowired
    private IQuartzService quartzService;

    @GetMapping("")
    public Object listQuartz(QuartzDTO dto) {
        return quartzService.listQuartz(dto);
    }

    @PostMapping("")
    public Object saveQuartz(Quartz quartz) {
        if (quartzService.saveQuartz(quartz)){
            response.setStatus(HttpServletResponse.SC_CREATED);
            return quartz;
        }
        return "添加定时任务失败";
    }

    @PutMapping("")
    public Object updateQuartz(Quartz quartz){
        if (quartzService.updateQuartz(quartz)){
            return quartz;
        }
        return "更新定时任务失败";
    }

    @DeleteMapping("/{id}")
    public Object removeQuartz(@PathVariable("id") int id){
        if (quartzService.removeQuartzById(id)){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }
        return "删除定时任务失败";
    }
}
