package com.cjt.dao.admin;

import com.cjt.common.dto.BasePageDto;
import com.cjt.entity.admin.Quartz;

import java.util.List;

public interface IQuartzDao {

  List<Quartz> listJobs(BasePageDto dto);

  int countJobs(BasePageDto dto);

  <T extends Quartz> int saveJob(T job);
}
