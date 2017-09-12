package com.cjt.ssm.quartz;

import org.quartz.Job;

/**
 * 定时计划基本信息
 */
public abstract class AbstractScheduleJob implements Job {

  /** 任务id */
  private String id;

  /** 任务名称 */
  private String jobName;

  /** 任务分组 */
  private String jobGroup;

  /** 任务状态 0禁用 1启用 */
  private String status;

  /** 任务运行时间表达式 */
  private String cronExpression;

  /** 任务描述 */
  private String desc;

  /** 目标类(bean) */
  private String targetObject;

  /** 目标方法 */
  private String targetMethod;

  public String getTargetObject() {
    return targetObject;
  }

  public void setTargetObject(String targetObject) {
    this.targetObject = targetObject;
  }

  public String getTargetMethod() {
    return targetMethod;
  }

  public void setTargetMethod(String targetMethod) {
    this.targetMethod = targetMethod;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getJobGroup() {
    return jobGroup;
  }

  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
