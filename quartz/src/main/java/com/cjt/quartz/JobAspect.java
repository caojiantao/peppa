package com.cjt.quartz;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务切面，用作解决集群部署任务单点执行
 *
 * @author caojiantao
 */
@Aspect
@Component
public class JobAspect {

    private final IQuartzExecuteService executeService;

    @Autowired
    public JobAspect(IQuartzExecuteService executeService) {
        this.executeService = executeService;
    }

    @Around("execution(* com.cjt.quartz.job..*.execute(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object context = joinPoint.getArgs()[0];
        if (context == null) {
            // 执行上下文为空代表手动执行
            result = joinPoint.proceed();
            return result;
        }
        String jobClass = ((JobExecutionContext) context).getJobDetail().getJobClass().getName();
        if (executeService.saveExecute(jobClass)) {
            result = joinPoint.proceed();
            executeService.removeExecuteByJobClass(jobClass);
        }
        return result;
    }
}
