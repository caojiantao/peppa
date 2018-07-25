package com.cjt.quartz.job;

import com.cjt.entity.model.system.Quartz;
import com.cjt.quartz.IQuartzService;
import com.cjt.quartz.QuartzJobManager;
import com.cjt.service.RedisLock;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式定时任务单次执行，
 *
 * @author caojiantao
 */
public abstract class BaseJob implements Job {

    private final Logger logger = Logger.getLogger(BaseJob.class);

    @Autowired
    private IQuartzService quartzService;

    @Autowired
    private QuartzJobManager manager;

    @Autowired
    private RedisLock lock;

    @Override
    public void execute(JobExecutionContext context) {
        int id = context.getJobDetail().getJobDataMap().getIntValue("id");
        Quartz quartz = quartzService.getQuartzById(id);
        if (quartz == null) {
            logger.error(context.getJobDetail().getJobClass() + "已被直接删除");
            manager.removeQuartz(context.getTrigger().getKey());
        } else {
            try {
                if ((new CronExpression(quartz.getCronExpre())).isSatisfiedBy(new Date())) {
                    // 表达式与当前匹配
                    executeUniqueQuartz(context);
                } else {
                    logger.error("表达式[" + quartz.getCronExpre() + "]与当前时间不匹配");
                    manager.removeQuartz(context.getTrigger().getKey());
                    manager.addJob(quartz);
                }
            } catch (ParseException e) {
                logger.error("表达式[" + quartz.getCronExpre() + "]解析错误");
                manager.removeQuartz(context.getTrigger().getKey());
            }
        }
    }

    private void executeUniqueQuartz(JobExecutionContext context) {
        String jobClass = context.getJobDetail().getJobClass().getName();
        String requestId = UUID.randomUUID().toString();
        try {
            // 设置过期时间为1小时
            if (lock.tryLock(jobClass, requestId, 1, TimeUnit.HOURS)) {
                logger.info(jobClass + "开始执行...");
                // 实际quartz执行逻辑
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
