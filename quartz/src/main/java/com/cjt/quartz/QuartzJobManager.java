package com.cjt.quartz;

import com.cjt.entity.model.system.quartz.QuartzDO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author caojiantao
 */
@Component
public class QuartzJobManager {

    private final Logger logger = LogManager.getLogger(QuartzJobManager.class);

    private final Scheduler scheduler;

    @Autowired
    public QuartzJobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加定时任务
     */
    @SuppressWarnings("unchecked")
    public void addJob(QuartzDO quartzDO) {
        // 根据name和group获取trigger key，判断是否已经存在该trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzDO.getName(), quartzDO.getGroup());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                // 新建一个job，并将ID作为携带数据
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(quartzDO.getJobClass()))
                        .withIdentity(quartzDO.getName(), quartzDO.getGroup())
                        .withDescription(quartzDO.getDesc())
                        .usingJobData("id", quartzDO.getId())
                        .build();
                // 新建一个trigger
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzDO.getCronExpression())
                        // 定时任务错过处理策略，避免resume时再次执行trigger
                        .withMisfireHandlingInstructionDoNothing();
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // scheduler设置job和trigger
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzDO.getCronExpression())
                        .withMisfireHandlingInstructionDoNothing();
                TriggerBuilder builder = trigger.getTriggerBuilder().withIdentity(triggerKey);
                trigger = builder.withSchedule(scheduleBuilder).build();
                // 根据trigger key重新设置trigger
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            // job状态暂停
            if (!quartzDO.getStatus()) {
                pauseJob(quartzDO);
            }
        } catch (SchedulerException | ClassNotFoundException e) {
            logger.error(quartzDO.getJobClass() + "添加报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 暂停定时任务
     */
    public void pauseJob(QuartzDO quartzDO) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(quartzDO.getName(), quartzDO.getGroup()));
        } catch (SchedulerException e) {
            logger.error(quartzDO.getJobClass() + "暂停报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 继续定时任务
     */
    public void resumeJob(QuartzDO quartzDO) {
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(quartzDO.getName(), quartzDO.getGroup()));
        } catch (SchedulerException e) {
            logger.error(quartzDO.getJobClass() + "继续报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 移除定时任务
     */
    public void removeQuartz(QuartzDO quartzDO) {
        removeQuartz(TriggerKey.triggerKey(quartzDO.getName(), quartzDO.getGroup()));
    }

    public void removeQuartz(TriggerKey key) {
        try {
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
        } catch (SchedulerException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 手动执行定时任务
     */
    public boolean executeJob(QuartzDO quartzDO) {
        try {
            scheduler.triggerJob(new JobKey(quartzDO.getName(), quartzDO.getGroup()));
            return true;
        } catch (SchedulerException e) {
            logger.error(quartzDO.getJobClass() + "手动执行报错：" + ExceptionUtils.getStackTrace(e));
            return false;
        }
    }
}
