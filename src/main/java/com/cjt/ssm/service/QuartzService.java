package com.cjt.ssm.service;

import com.cjt.ssm.dto.BasePageDto;
import com.cjt.ssm.quartz.jobs.BaseJob;

import java.util.List;

public interface QuartzService {

  List<BaseJob> listJobs();

  List<BaseJob> listJobs(BasePageDto dto);

  int countJobs(BasePageDto dto);

  <T extends BaseJob> boolean saveQuartz(T job);
}
