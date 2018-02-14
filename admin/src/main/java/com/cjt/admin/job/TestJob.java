package com.cjt.admin.job;

import com.cjt.service.http.service.IJapaneseService;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author caojiantao
 */
@Component
public class TestJob implements Job {

    @Autowired
    private IJapaneseService japaneseService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        if (jobDetail != null) {
            System.out.println(jobDetail.getDescription() + ":执行");
        }
    }
}
