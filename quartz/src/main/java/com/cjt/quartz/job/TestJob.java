package com.cjt.quartz.job;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TestJob extends BaseJob {

    private Logger logger = Logger.getLogger(TestJob.class);

    @Override
    public void doExecute() {
    }
}
