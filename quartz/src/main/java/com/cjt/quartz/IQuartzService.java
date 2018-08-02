package com.cjt.quartz;

import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.system.quartz.QuartzDO;

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
    List<QuartzDO> listQuartz(QuartzDTO dto);

    int countQuartz(QuartzDTO dto);

    /**
     * 提交定时任务
     *
     * @param quartzDO 定时任务
     * @return 成功与否
     */
    boolean saveQuartz(QuartzDO quartzDO);

    /**
     * 暂停定时任务
     *
     * @param id 定时任务id
     * @return 成功与否
     */
    boolean pauseQuartzById(int id);

    /**
     * 继续定时任务
     *
     * @param id 定时任务id
     * @return 成功与否
     */
    boolean resumeQuartzById(int id);

    /**
     * 移除定时任务
     *
     * @param id 定时任务id
     * @return 成功与否
     */
    boolean deleteQuartzById(int id);

    /**
     * 获取指定定时任务
     *
     * @param id 定时任务id
     * @return 定时任务信息
     */
    QuartzDO getQuartzById(int id);

    boolean executeQuartzById(int id);
}
