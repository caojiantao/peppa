package com.cjt.dao;

import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzDAO {

    Quartz getQuartzById(int id);

    /**
     * 获取条件范围内的定时任务集合
     *
     * @param dto 定时任务查询DTO
     * @return 定时任务集合
     */
    List<Quartz> listQuartz(BasePageDTO dto);

    /**
     * 获取条件范围内的定时任务数量
     *
     * @param dto 定时任务查询DTO
     * @return 定时任务数量
     */
    int countQuartz(BasePageDTO dto);

    /**
     * 添加定时任务
     */
    void saveQuartz(Quartz quartz);

    int updateQuartz(Quartz quartz);

    int removeQuartzById(int id);
}
