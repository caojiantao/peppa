package com.cjt.ssm.quartz;

import com.cjt.ssm.service.UserService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class QuartzJobManager {

  @Resource
  private Scheduler scheduler;

  @Resource
  private UserService userService;

  /**
   * 添加定时任务
   */
  public void addJob(AbstractScheduleJob job) {
    // 根据name和group获取trigger key，判断是否已经存在该trigger
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
    try {
      Trigger trigger = scheduler.getTrigger(triggerKey);
      if (trigger == null) {
        // 新建一个job
        JobDetail jobDetail = JobBuilder.newJob(AbstractScheduleJob.class)
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withDescription(job.getDesc())
                .build();

        // 新建一个trigger
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder)
                .build();

        // scheduler设置job和trigger
        scheduler.scheduleJob(jobDetail, trigger);
      } else {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        TriggerBuilder builder = trigger.getTriggerBuilder().withIdentity(triggerKey);
        trigger = builder.withSchedule(scheduleBuilder).build();
        // 根据trigger key重新设置trigger
        scheduler.rescheduleJob(triggerKey, trigger);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /*public List<TestJob> getAllJobs() {
    List<AbstractScheduleJob> jobList = new ArrayList<>();
    try {
      GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
      Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
      for (JobKey jobKey : jobKeySet) {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        for (Trigger trigger : triggers) {
          TestJob job
          job.setJobName(jobKey.getName());
          job.setJobGroup(jobKey.getGroup());
          job.setDesc("触发器:" + trigger.getKey());

          Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
          job.setJobStatus(triggerState.name());
          if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            job.setCronExpression(cronTrigger.getCronExpression());
          }
          jobList.add(job);
        }
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return jobList;
  }

  public List<AbstractScheduleJob> getAllExecutingJobs() {
    List<AbstractScheduleJob> jobList = null;
    try {
      List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
      jobList = new ArrayList<>(executingJobs.size());
      for (JobExecutionContext executingJob : executingJobs) {
        AbstractScheduleJob job = new AbstractScheduleJob();
        JobDetail jobDetail = executingJob.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        Trigger trigger = executingJob.getTrigger();
        job.setJobName(jobKey.getName());
        job.setJobGroup(jobKey.getGroup());
        job.setDesc("触发器:" + trigger.getKey());

        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        job.setJobStatus(triggerState.name());
        if (trigger instanceof CronTrigger) {
          CronTrigger cronTrigger = (CronTrigger) trigger;
          String cronExpression = cronTrigger.getCronExpression();
          job.setCronExpression(cronExpression);
        }
        jobList.add(job);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return jobList;
  }*/
}
