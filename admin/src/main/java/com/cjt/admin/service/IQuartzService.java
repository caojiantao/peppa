package com.cjt.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzService {

    /**
     * 分页获取条件范围内的定时任务
     *
     * @param dto 条件DTO
     * @return 定时任务集合
     */
    JSONObject listQuartz(QuartzDTO dto);

    /**
     * 添加定时任务
     */
    boolean saveQuartz(Quartz quartz);

    boolean updateQuartz(Quartz quartz);

    boolean removeQuartzById(int id);
}
