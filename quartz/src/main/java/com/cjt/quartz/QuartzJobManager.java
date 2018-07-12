package com.cjt.quartz;

import com.cjt.entity.model.system.Quartz;
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
    public void addJob(Quartz job) {
        // 根据name和group获取trigger key，判断是否已经存在该trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                // 新建一个job
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                        .withIdentity(job.getName(), job.getGroup())
                        .withDescription(job.getDesc())
                        .build();
                // 新建一个trigger
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpre())
                        // 定时任务错过处理策略，避免resume时再次执行trigger
                        .withMisfireHandlingInstructionDoNothing();
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // scheduler设置job和trigger
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpre())
                        .withMisfireHandlingInstructionDoNothing();
                TriggerBuilder builder = trigger.getTriggerBuilder().withIdentity(triggerKey);
                trigger = builder.withSchedule(scheduleBuilder).build();
                // 根据trigger key重新设置trigger
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            // job状态暂停
            if (!job.getStatus()) {
                pauseJob(job);
            }
        } catch (SchedulerException | ClassNotFoundException e) {
            logger.error(job.getJobClass() + "添加报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 暂停定时任务
     */
    public void pauseJob(Quartz job) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error(job.getJobClass() + "暂停报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 继续定时任务
     */
    public void resumeJob(Quartz job) {
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error(job.getJobClass() + "继续报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 移除定时任务
     */
    public void removeJob(Quartz quartz) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(quartz.getName(), quartz.getGroup()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(quartz.getName(), quartz.getGroup()));
        } catch (SchedulerException e) {
            logger.error(quartz.getJobClass() + "移除报错：" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 手动执行定时任务
     */
    public boolean executeJob(Quartz quartz) {
        try {
            scheduler.triggerJob(new JobKey(quartz.getName(), quartz.getGroup()));
            return true;
        } catch (SchedulerException e) {
            logger.error(quartz.getJobClass() + "手动执行报错：" + ExceptionUtils.getStackTrace(e));
            return false;
        }
    }
}
