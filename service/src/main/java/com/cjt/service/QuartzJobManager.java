package com.cjt.service;

import com.cjt.entity.model.job.Quartz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author caojiantao
 */
@Component
public class QuartzJobManager {

    private Logger logger = LogManager.getLogger(QuartzJobManager.class);

    @Resource
    private Scheduler scheduler;

    /**
     * 添加定时任务
     */
    public <T extends Quartz> void addJob(T job) {
        // 根据name和group获取trigger key，判断是否已经存在该trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                logger.info("====================【" + job.getDesc() + "】添加====================");
                // 新建一个job
                JobDetail jobDetail = JobBuilder.newJob(job.getClass())
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
            if (!job.getStatus()) {
                pauseJob(job);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public <T extends Quartz> void pauseJob(T job) {
        logger.info("====================【" + job.getDesc() + "】暂停====================");
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】暂停失败", e);
            e.printStackTrace();
        }
    }

    public <T extends Quartz> void resumeJob(T job) {
        logger.info("====================【" + job.getDesc() + "】恢复====================");
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】恢复失败", e);
            e.printStackTrace();
        }
    }

    public <T extends Quartz> void removeJob(T job) {
        logger.info("====================【" + job.getDesc() + "】移除====================");
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error("【" + job.getDesc() + "】移除失败", e);
            e.printStackTrace();
        }
    }
}
