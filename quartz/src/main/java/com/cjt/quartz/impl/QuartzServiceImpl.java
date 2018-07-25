package com.cjt.quartz.impl;

import com.alibaba.fastjson.JSONObject;
import com.caojiantao.common.util.JsonUtils;
import com.cjt.dao.system.IQuartzDAO;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.system.Quartz;
import com.cjt.quartz.IQuartzService;
import com.cjt.quartz.QuartzJobManager;
import org.apache.commons.lang3.StringUtils;
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

    private final Logger logger = LogManager.getLogger(QuartzServiceImpl.class);

    private final QuartzJobManager quartzJobManager;

    private final IQuartzDAO quartzDao;

    @Autowired
    public QuartzServiceImpl(QuartzJobManager quartzJobManager, IQuartzDAO quartzDao) {
        this.quartzJobManager = quartzJobManager;
        this.quartzDao = quartzDao;
    }

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
        String cron = quartz.getCronExpre();
        if (quartzDao.updateQuartz(quartz) > 0) {
            // 根据cron表达式区分更新的动作（更新、暂停、继续）
            if (StringUtils.isNotBlank(cron)) {
                quartzJobManager.addJob(quartz);
            } else {
                quartz = quartzDao.getQuartzById(quartz.getId());
                boolean status = quartz.getStatus();
                if (status) {
                    quartzJobManager.resumeJob(quartz);
                } else {
                    quartzJobManager.pauseJob(quartz);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean pauseQuartzById(int id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        if (quartz != null) {
            quartzJobManager.pauseJob(quartz);
            return true;
        }
        return false;
    }

    @Override
    public boolean resumeQuartzById(int id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        if (quartz != null) {
            quartzJobManager.resumeJob(quartz);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeQuartzById(int id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        if (quartzDao.removeQuartzById(id) > 0) {
            quartzJobManager.removeQuartz(quartz);
        }
        return false;
    }

    @Override
    public Quartz getQuartzById(int id) {
        return quartzDao.getQuartzById(id);
    }

    @Override
    public boolean executeQuartzById(int id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        return quartzJobManager.executeJob(quartz);
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
