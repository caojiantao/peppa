package com.cjt.dao.system;

import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.system.Quartz;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzDAO {

    /**
     * 获取指定定时任务
     *
     * @param id 定时任务ID
     * @return 定时任务
     */
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
     *
     * @param quartz 定时任务
     */
    void saveQuartz(Quartz quartz);

    /**
     * 更新定时任务
     *
     * @param quartz 定时任务
     * @return 影响行数
     */
    int updateQuartz(Quartz quartz);

    /**
     * 删除指定定时任务
     *
     * @param id 定时任务ID
     * @return 影响行数
     */
    int removeQuartzById(int id);
}
