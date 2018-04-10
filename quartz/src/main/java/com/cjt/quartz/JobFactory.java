package com.cjt.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author caojiantao
 * @since 2018-02-13 19:59:48
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    private final ApplicationContext context;

    @Autowired
    public JobFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) {
        return context.getBean(bundle.getJobDetail().getJobClass());
    }
}
