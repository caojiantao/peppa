package com.cjt.ssm.service.impl;

import com.cjt.ssm.dao.QuartzDao;
import com.cjt.ssm.quartz.ScheduleJob;
import com.cjt.ssm.service.QuartzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuartzServiceImpl implements QuartzService {

  @Resource
  private QuartzDao quartzDao;

  @Override
  public List<ScheduleJob> listJobs() {
    return quartzDao.listJobs();
  }

  @Override
  public <T extends ScheduleJob> boolean saveQuartz(T job) {
    return quartzDao.saveScheduleJob(job) > 0;
  }
}
