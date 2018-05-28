package com.cjt.quartz;

import com.cjt.service.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务切面，用作解决集群部署任务单点执行
 *
 * @author caojiantao
 */
@Aspect
@Component
public class JobAspect {

    private final RedisLock lock;

    @Autowired
    public JobAspect(RedisLock lock) {
        this.lock = lock;
    }

    @Around("execution(* com.cjt.quartz.job..*.execute(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        String jobClass = "";
        String requestId = UUID.randomUUID().toString();
        try {
            Object context = joinPoint.getArgs()[0];
            if (context == null) {
                // 执行上下文为空代表手动执行
                result = joinPoint.proceed();
                return result;
            }
            jobClass = ((JobExecutionContext) context).getJobDetail().getJobClass().getName();
            // 设置过期时间为1小时
            if (lock.tryLock(jobClass, requestId, 1, TimeUnit.HOURS)) {
                result = joinPoint.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            // 保证会释放锁
            lock.releaseLock(jobClass, requestId);
        }
        return result;
    }
}
