package com.cjt.quartz.job;

import com.cjt.service.RedisLock;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author caojiantao
 */
public abstract class BaseJob implements Job {

    private final Logger logger = Logger.getLogger(BaseJob.class);

    @Autowired
    private RedisLock lock;

    @Override
    public void execute(JobExecutionContext context) {
        String jobClass = context.getJobDetail().getJobClass().getName();
        String requestId = UUID.randomUUID().toString();
        try {
            // 设置过期时间为1小时
            if (lock.tryLock(jobClass, requestId, 1, TimeUnit.HOURS)) {
                logger.info(jobClass + "开始执行...");
                doExecute();
            } else {
                logger.info(jobClass + "获取执行锁失败");
            }
        } catch (Throwable e) {
            logger.error("执行定时任务出现错误：" + ExceptionUtils.getStackTrace(e));
        } finally {
            // 保证会释放锁
            lock.releaseLock(jobClass, requestId);
        }
    }

    /**
     * 定时任务实际业务逻辑
     */
    public abstract void doExecute();
}
