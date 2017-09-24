package com.cjt.backend.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;


/**
 * 定时计划基本信息
 */
public class BaseJob implements Job, Serializable{

  /** 任务id */
  private Integer id;

  /** 任务名称 */
  private String name;

  /** 任务分组 */
  private String group;

  /** 任务状态 */
  private Boolean status;

  /** 任务运行时间表达式 */
  private String cronExpre;

  /** 任务描述 */
  private String desc;

  /** 删除标识 */
  private Boolean deleted;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getCronExpre() {
    return cronExpre;
  }

  public void setCronExpre(String cronExpre) {
    this.cronExpre = cronExpre;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobDetail jobDetail = jobExecutionContext.getJobDetail();
    if (jobDetail != null){
      System.out.println(jobDetail.getDescription() + ":执行");
    }
  }
}
