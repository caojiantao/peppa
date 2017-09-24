package com.cjt.backend.service.impl;

import com.cjt.backend.dao.QuartzDao;
import com.cjt.backend.jobs.BaseJob;
import com.cjt.backend.quartz.QuartzJobManager;
import com.cjt.backend.service.QuartzService;
import com.cjt.common.dto.BasePageDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.Cacheable;
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

  @Cacheable("quartzCache")
  @Override
  public BaseJob getQuartz(String name) {
    System.out.println("操作");
    BaseJob job = new BaseJob();
    job.setName(name);
    return job;
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
