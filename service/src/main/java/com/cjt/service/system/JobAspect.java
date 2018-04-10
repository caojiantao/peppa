package com.cjt.service.system;

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

    @Around("execution(* com.cjt.service.system.job..*.execute(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String jobClass = ((JobExecutionContext) args[0]).getJobDetail().getJobClass().getName();
        Object result = null;
        System.out.println("条件判断 ------------");
        if (executeService.saveExecute(jobClass)) {
            result = joinPoint.proceed();
            executeService.removeExecuteByJobClass(jobClass);
        }
        return result;
    }
}
