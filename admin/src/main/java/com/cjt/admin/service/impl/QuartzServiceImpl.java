package com.cjt.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.service.IQuartzService;
import com.cjt.admin.service.QuartzJobManager;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.IQuartzDAO;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.job.Quartz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class QuartzServiceImpl implements IQuartzService, InitializingBean {

    private Logger logger = LogManager.getLogger(QuartzServiceImpl.class);

    @Autowired
    private QuartzJobManager quartzJobManager;

    @Autowired
    private IQuartzDAO quartzDao;

    @Override
    public JSONObject listQuartz(QuartzDTO dto) {
        List<Quartz> quartzs = quartzDao.listQuartz(dto);
        int total = quartzDao.countQuartz(dto);
        return JsonUtils.toPageData(quartzs, total);
    }

    @Override
    public boolean saveQuartz(Quartz quartz) {
        quartzDao.saveQuartz(quartz);
        if (quartz.getId() > 0) {
            quartzJobManager.addJob(quartz);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateQuartz(Quartz quartz) {
        if (quartzDao.updateQuartz(quartz) > 0) {
            quartzJobManager.addJob(quartz);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeQuartzById(int id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        if (quartzDao.removeQuartzById(id) > 0){
            quartzJobManager.removeJob(quartz);
        }
        return false;
    }

    /**
     * bean初始化，加载一次
     */
    @Override
    public void afterPropertiesSet() {
        logger.info("====================【开始初始化定时任务】====================");
        List<Quartz> tasks = quartzDao.listQuartz(null);
        for (Quartz task : tasks) {
            quartzJobManager.addJob(task);
        }
    }
}
