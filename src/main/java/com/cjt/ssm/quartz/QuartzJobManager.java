package com.cjt.ssm.quartz;

import com.cjt.ssm.service.QuartzService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class QuartzJobManager implements InitializingBean {

  private Logger logger = Logger.getLogger(QuartzJobManager.class);

  @Resource
  private Scheduler scheduler;

  @Resource
  private QuartzService quartzService;

  /**
   * bean初始化，加载一次
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    logger.info("====================【开始初始化定时任务】====================");
    List<ScheduleJob> tasks = quartzService.listJobs();
    for (ScheduleJob task : tasks) {
      addJob(task);
    }
  }

  /**
   * 添加定时任务
   */
  public <T extends ScheduleJob> void addJob(T job) {
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
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
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
      if (!job.getStatus()){
        pauseJob(job);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  public <T extends ScheduleJob> void pauseJob(T job) {
    logger.info("====================【" + job.getDesc() + "】暂停====================");
    try {
      scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
    } catch (SchedulerException e) {
      logger.error("【" + job.getDesc() + "】暂停失败", e);
      e.printStackTrace();
    }
  }

  public <T extends ScheduleJob> void resumeJob(T job) {
    logger.info("====================【" + job.getDesc() + "】恢复====================");
    try {
      scheduler.resumeTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
    } catch (SchedulerException e) {
      logger.error("【" + job.getDesc() + "】恢复失败", e);
      e.printStackTrace();
    }
  }

  public <T extends ScheduleJob> void removeJob(T job) {
    logger.info("====================【" + job.getDesc() + "】移除====================");
    try {
      scheduler.pauseTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
      scheduler.unscheduleJob(TriggerKey.triggerKey(job.getName(), job.getGroup()));
    } catch (SchedulerException e) {
      logger.error("【" + job.getDesc() + "】移除失败", e);
      e.printStackTrace();
    }
  }

  /*public List<TestJob> getAllJobs() {
    List<ScheduleJob> jobList = new ArrayList<>();
    try {
      GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
      Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
      for (JobKey jobKey : jobKeySet) {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        for (Trigger trigger : triggers) {
          TestJob job
          job.setName(jobKey.getName());
          job.setGroup(jobKey.getGroup());
          job.setDesc("触发器:" + trigger.getKey());

          Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
          job.setJobStatus(triggerState.name());
          if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            job.setCronExpre(cronTrigger.getCronExpre());
          }
          jobList.add(job);
        }
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return jobList;
  }

  public List<ScheduleJob> getAllExecutingJobs() {
    List<ScheduleJob> jobList = null;
    try {
      List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
      jobList = new ArrayList<>(executingJobs.size());
      for (JobExecutionContext executingJob : executingJobs) {
        ScheduleJob job = new ScheduleJob();
        JobDetail jobDetail = executingJob.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        Trigger trigger = executingJob.getTrigger();
        job.setName(jobKey.getName());
        job.setGroup(jobKey.getGroup());
        job.setDesc("触发器:" + trigger.getKey());

        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        job.setJobStatus(triggerState.name());
        if (trigger instanceof CronTrigger) {
          CronTrigger cronTrigger = (CronTrigger) trigger;
          String cronExpression = cronTrigger.getCronExpre();
          job.setCronExpre(cronExpression);
        }
        jobList.add(job);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return jobList;
  }*/
}
