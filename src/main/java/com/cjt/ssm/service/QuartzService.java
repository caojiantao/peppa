package com.cjt.ssm.service;

import com.cjt.ssm.quartz.ScheduleJob;

import java.util.List;

public interface QuartzService {

  List<ScheduleJob> listJobs();

  <T extends ScheduleJob> boolean saveQuartz(T job);
}
