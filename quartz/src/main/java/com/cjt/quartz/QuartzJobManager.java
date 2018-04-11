package com.cjt.quartz;

import com.cjt.common.util.ExceptionUtils;
import com.cjt.entity.model.system.Quartz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author caojiantao
 */
@Component
public class QuartzJobManager {

    private final Logger logger = LogManager.getLogger(QuartzJobManager.class);

    private final Scheduler scheduler;

    private final ApplicationContext context;

    @Autowired
    public QuartzJobManager(Scheduler scheduler, ApplicationContext context) {
        this.scheduler = scheduler;
        this.context = context;
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
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void pauseJob(Quartz job) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void resumeJob(Quartz job) {
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public void removeJob(Quartz job) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        } catch (SchedulerException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
    }

    public boolean executeJob(String clazz) {
        try {
            Class<?> jobClass = Class.forName(clazz);
            Object job = context.getBean(jobClass);
            jobClass.getDeclaredMethod("execute", JobExecutionContext.class).invoke(job, (Object) null);
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
            return false;
        }
    }
}
