package com.cjt.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzService {

    /**
     * 获取所有定时任务
     *
     * @return 所有定时任务
     */
    JSONObject listJobs();

    /**
     * 分页获取条件范围内的定时任务
     *
     * @param dto 条件DTO
     * @return 定时任务集合
     */
    JSONObject listJobs(BasePageDTO dto);

    /**
     * 添加定时任务
     *
     * @param job 定时任务
     * @param <T> 任务class
     * @return 成功与否
     */
    <T extends Quartz> boolean saveQuartz(T job);

    /**
     * 获取指定name的定时任务
     *
     * @param name 定时任务名称
     * @return 定时任务
     */
    Quartz getQuartz(String name);
}
