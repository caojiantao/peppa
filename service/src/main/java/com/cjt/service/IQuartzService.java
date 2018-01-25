package com.cjt.service;

import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

public interface IQuartzService {

  List<Quartz> listJobs();

  List<Quartz> listJobs(BasePageDTO dto);

  int countJobs(BasePageDTO dto);

  <T extends Quartz> boolean saveQuartz(T job);

  Quartz getQuartz(String name);
}
