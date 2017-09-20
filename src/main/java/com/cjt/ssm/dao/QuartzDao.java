package com.cjt.ssm.dao;

import com.cjt.ssm.dto.BasePageDto;
import com.cjt.ssm.quartz.jobs.BaseJob;

import java.util.List;

public interface QuartzDao {

  List<BaseJob> listJobs(BasePageDto dto);

  int countJobs(BasePageDto dto);

  <T extends BaseJob> int saveJob(T job);
}
