package com.cjt.admin.controller;

import com.cjt.entity.dto.ResultDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caojiantao
 */
public class BaseController {

    private Logger logger = LogManager.getLogger(BaseController.class);

    /**
     * 采用注解方式统一处理服务器未捕获异常（500：服务器内部错误）
     */
    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception ex) {
        logger.error(ExceptionUtils.getStackTrace(ex));
        return failure("系统异常");
    }

    protected ResultDTO success(String msg) {
        return new ResultDTO<>(true, null, msg);
    }

    protected <T> ResultDTO success(String msg, T data) {
        return new ResultDTO<>(true, data, msg);
    }

    protected ResultDTO failure(String msg) {
        return new ResultDTO<>(false, null, msg);
    }
}
