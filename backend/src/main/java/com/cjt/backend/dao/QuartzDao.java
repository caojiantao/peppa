package com.cjt.backend.dao;

import com.cjt.common.dto.BasePageDto;
import com.cjt.backend.jobs.BaseJob;

import java.util.List;

public interface QuartzDao {

  List<BaseJob> listJobs(BasePageDto dto);

  int countJobs(BasePageDto dto);

  <T extends BaseJob> int saveJob(T job);
}
