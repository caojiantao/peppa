package com.cjt.quartz;

public interface IQuartzExecuteService {

    boolean saveExecute(String jobClass);

    boolean removeExecuteByJobClass(String jobClass);
}
