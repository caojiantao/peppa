package com.cjt.dao;

import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzDAO {

    /**
     * 获取条件范围内的定时任务集合
     *
     * @param dto 定时任务查询DTO
     * @return 定时任务集合
     */
    List<Quartz> listJobs(BasePageDTO dto);

    /**
     * 获取条件范围内的定时任务数量
     *
     * @param dto 定时任务查询DTO
     * @return 定时任务数量
     */
    int countJobs(BasePageDTO dto);

    /**
     * 添加定时任务
     *
     * @param job 定时任务
     * @param <T> 定时任务class
     */
    <T extends Quartz> void saveJob(T job);
}
