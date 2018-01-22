package com.cjt.dao.admin;

import com.cjt.common.dto.BasePageDTO;
import com.cjt.entity.admin.Quartz;

import java.util.List;

public interface IQuartzDAO {

  List<Quartz> listJobs(BasePageDTO dto);

  int countJobs(BasePageDTO dto);

  <T extends Quartz> int saveJob(T job);
}
