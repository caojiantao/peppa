package com.cjt.dao;

import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;

import java.util.List;

public interface IQuartzDAO {

  List<Quartz> listJobs(BasePageDTO dto);

  int countJobs(BasePageDTO dto);

  <T extends Quartz> int saveJob(T job);
}
