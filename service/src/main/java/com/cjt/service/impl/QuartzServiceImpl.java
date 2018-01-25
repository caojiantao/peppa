package com.cjt.service.impl;

import com.cjt.common.dto.BasePageDTO;
import com.cjt.dao.IQuartzDAO;
import com.cjt.entity.job.Quartz;
import com.cjt.service.IQuartzService;
import com.cjt.service.QuartzJobManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class QuartzServiceImpl implements IQuartzService,InitializingBean {

  private Logger logger = LogManager.getLogger(getClass());

  @Resource
  private QuartzJobManager quartzJobManager;

  @Resource
  private IQuartzDAO quartzDao;

  @Override
  public List<Quartz> listJobs() {
    return listJobs(null);
  }

  @Override
  public List<Quartz> listJobs(BasePageDTO dto) {
    return quartzDao.listJobs(dto);
  }

  @Override
  public int countJobs(BasePageDTO dto) {
    return quartzDao.countJobs(dto);
  }

  @Override
  public <T extends Quartz> boolean saveQuartz(T job) {
    if (quartzDao.saveJob(job) < 0){
      return false;
    }
    quartzJobManager.addJob(job);
    return true;
  }

  @Cacheable("quartzCache")
  @Override
  public Quartz getQuartz(String name) {
    System.out.println("操作");
    Quartz job = new Quartz();
    job.setName(name);
    return job;
  }

  /**
   * bean初始化，加载一次
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    logger.info("====================【开始初始化定时任务】====================");
    List<Quartz> tasks = listJobs();
    for (Quartz task : tasks) {
      quartzJobManager.addJob(task);
    }
  }
}
