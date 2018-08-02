package com.cjt.quartz.impl;

import com.cjt.dao.system.quartz.IQuartzDAO;
import com.cjt.entity.dto.QuartzDTO;
import com.cjt.entity.model.system.quartz.QuartzDO;
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

    private final IQuartzDAO quartzDAO;

    @Autowired
    public QuartzServiceImpl(QuartzJobManager quartzJobManager, IQuartzDAO quartzDAO) {
        this.quartzJobManager = quartzJobManager;
        this.quartzDAO = quartzDAO;
    }

    @Override
    public List<QuartzDO> listQuartz(QuartzDTO dto) {
        return quartzDAO.listObjects(dto);
    }

    @Override
    public int countQuartz(QuartzDTO dto) {
        return quartzDAO.countObjects(dto);
    }

    @Override
    public boolean saveQuartz(QuartzDO quartzDO) {
        if (quartzDO.getId() == null){
            quartzDAO.insert(quartzDO);
            if (quartzDO.getId() > 0) {
                if (quartzDO.getStatus()) {
                    quartzJobManager.addJob(quartzDO);
                }
                return true;
            } else {
                return false;
            }
        } else {
            if (quartzDAO.updateById(quartzDO) > 0) {
                if (quartzDO.getStatus()) {
                    quartzJobManager.addJob(quartzDO);
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean pauseQuartzById(int id) {
        QuartzDO quartzDO = quartzDAO.getById(id);
        if (quartzDO != null) {
            quartzJobManager.pauseJob(quartzDO);
            return true;
        }
        return false;
    }

    @Override
    public boolean resumeQuartzById(int id) {
        QuartzDO quartzDO = quartzDAO.getById(id);
        if (quartzDO != null) {
            quartzJobManager.resumeJob(quartzDO);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteQuartzById(int id) {
        QuartzDO quartzDO = quartzDAO.getById(id);
        if (quartzDAO.deleteById(id) > 0) {
            quartzJobManager.removeQuartz(quartzDO);
        }
        return false;
    }

    @Override
    public QuartzDO getQuartzById(int id) {
        return quartzDAO.getById(id);
    }

    @Override
    public boolean executeQuartzById(int id) {
        QuartzDO quartzDO = quartzDAO.getById(id);
        return quartzJobManager.executeJob(quartzDO);
    }

    /**
     * bean初始化，加载一次
     */
    @Override
    public void afterPropertiesSet() {
        logger.info("====================【开始初始化定时任务】====================");
        List<QuartzDO> tasks = quartzDAO.listObjects(null);
        for (QuartzDO task : tasks) {
            quartzJobManager.addJob(task);
        }
    }
}
