package com.cjt.admin.service;

import com.cjt.common.util.ExceptionUtils;
import com.cjt.entity.model.job.Quartz;
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
                logger.info("【" + job.getDesc() + "】添加");
                // 新建一个job
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                        .withIdentity(job.getName(), job.getGroup())
                        .withDescription(job.getDesc())
                        .build();
                // 新建一个trigger
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpre());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // scheduler设置job和trigger
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpre());
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
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void pauseJob(Quartz job) {
        logger.info("【" + job.getDesc() + "】暂停");
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】暂停失败", e);
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void resumeJob(Quartz job) {
        logger.info("【" + job.getDesc() + "】恢复");
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】恢复失败", e);
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void removeJob(Quartz job) {
        logger.info("【" + job.getDesc() + "】移除");
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】移除失败", e);
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }
}
