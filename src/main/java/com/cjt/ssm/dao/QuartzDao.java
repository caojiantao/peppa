package com.cjt.ssm.dao;

import com.cjt.ssm.quartz.ScheduleJob;

import java.util.List;

public interface QuartzDao {

  List<ScheduleJob> listJobs();

  <T extends ScheduleJob> int  saveScheduleJob(T job);
}
