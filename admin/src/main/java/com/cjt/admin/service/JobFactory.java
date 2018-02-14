package com.cjt.admin.service;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @author caojiantao
 * @since 2018-02-13 19:59:48
 */
public class JobFactory extends AdaptableJobFactory implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) {
        return context.getBean(bundle.getJobDetail().getJobClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
