package com.cjt.service.system.impl;

import com.cjt.dao.system.IQuartzExecuteDAO;
import com.cjt.service.system.IQuartzExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class QuartzExecuteServiceImpl implements IQuartzExecuteService {

    private final IQuartzExecuteDAO executeDAO;

    @Autowired
    public QuartzExecuteServiceImpl(IQuartzExecuteDAO executeDAO) {
        this.executeDAO = executeDAO;
    }

    @Override
    public boolean saveExecute(String jobClass) {
        try {
            // 依靠唯一性索引解决定时任务集群部署问题
            executeDAO.saveExecute(jobClass);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeExecuteByJobClass(String jobClass) {
        return executeDAO.removeExecuteByJobClass(jobClass) > 0;
    }
}
