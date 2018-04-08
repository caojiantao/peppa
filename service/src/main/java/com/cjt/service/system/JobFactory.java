package com.cjt.service.system;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @author caojiantao
 * @since 2018-02-13 19:59:48
 */
public class JobFactory extends AdaptableJobFactory {

    @Autowired
    private ApplicationContext context;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) {
        return context.getBean(bundle.getJobDetail().getJobClass());
    }
}
