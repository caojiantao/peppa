package com.cjt.quartz;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.system.Quartz;

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
     *
     * @param quartz 定时任务
     * @return 成功与否
     */
    boolean saveQuartz(Quartz quartz);

    /**
     * 更新定时任务
     *
     * @param quartz 定时任务
     * @return 成功与否
     */
    boolean updateQuartz(Quartz quartz);

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
    boolean removeQuartzById(int id);

    /**
     * 获取指定定时任务
     *
     * @param id 定时任务id
     * @return 定时任务信息
     */
    Quartz getQuartzById(int id);

    boolean executeQuartzById(int id);
}
