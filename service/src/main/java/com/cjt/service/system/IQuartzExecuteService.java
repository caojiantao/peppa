package com.cjt.service.system;

public interface IQuartzExecuteService {

    boolean saveExecute(String jobClass);

    boolean removeExecuteByJobClass(String jobClass);
}
