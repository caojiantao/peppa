package com.cjt.entity.model.system;

/**
 * 定时计划基本信息
 *
 * @author caojiantao
 */
public class Quartz{

    /**
     * 任务id
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务分组
     */
    private String group;

    /**
     * 任务状态
     */
    private Boolean status;

    /**
     * 任务运行时间表达式
     */
    private String cronExpre;

    /**
     * 任务描述
     */
    private String desc;

    private String jobClass;

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

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }
}
