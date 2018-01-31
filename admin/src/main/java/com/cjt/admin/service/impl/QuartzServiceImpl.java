package com.cjt.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.service.IQuartzService;
import com.cjt.admin.service.QuartzJobManager;
import com.cjt.dao.IQuartzDAO;
import com.cjt.entity.dto.BasePageDTO;
import com.cjt.entity.model.job.Quartz;
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
public class QuartzServiceImpl implements IQuartzService, InitializingBean {

    private Logger logger = LogManager.getLogger(getClass());

    @Resource
    private QuartzJobManager quartzJobManager;

    @Resource
    private IQuartzDAO quartzDao;

    @Override
    public JSONObject listJobs() {
        return listJobs(null);
    }

    @Override
    public JSONObject listJobs(BasePageDTO dto) {
        JSONObject result = new JSONObject();
        List<Quartz> quartzs = quartzDao.listJobs(dto);
        int total = quartzDao.countJobs(dto);
        result.put("data", quartzs);
        result.put("total", total);
        return result;
    }

    @Override
    public <T extends Quartz> boolean saveQuartz(T job) {
        quartzDao.saveJob(job);
        if (job.getId() < 0) {
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
    public void afterPropertiesSet() {
        logger.info("====================【开始初始化定时任务】====================");
        List<Quartz> tasks = quartzDao.listJobs(null);
        for (Quartz task : tasks) {
            quartzJobManager.addJob(task);
        }
    }
}
