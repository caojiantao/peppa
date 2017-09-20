package com.cjt.ssm.service.impl;

import com.cjt.ssm.dao.QuartzDao;
import com.cjt.ssm.dto.BasePageDto;
import com.cjt.ssm.quartz.QuartzJobManager;
import com.cjt.ssm.quartz.jobs.BaseJob;
import com.cjt.ssm.service.QuartzService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class QuartzServiceImpl implements QuartzService,InitializingBean {

  private Logger logger = Logger.getLogger(getClass());

  @Resource
  private QuartzJobManager quartzJobManager;

  @Resource
  private QuartzDao quartzDao;

  @Override
  public List<BaseJob> listJobs() {
    return listJobs(null);
  }

  @Override
  public List<BaseJob> listJobs(BasePageDto dto) {
    return quartzDao.listJobs(dto);
  }

  @Override
  public int countJobs(BasePageDto dto) {
    return quartzDao.countJobs(dto);
  }

  @Override
  public <T extends BaseJob> boolean saveQuartz(T job) {
    if (quartzDao.saveJob(job) < 0){
      return false;
    }
    quartzJobManager.addJob(job);
    return true;
  }

  /**
   * bean初始化，加载一次
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    logger.info("====================【开始初始化定时任务】====================");
    List<BaseJob> tasks = listJobs();
    for (BaseJob task : tasks) {
      quartzJobManager.addJob(task);
    }
  }
}
