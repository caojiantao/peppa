package com.cjt.admin.controller;

import com.cjt.entity.dto.ResultDTO;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caojiantao
 */
@Log4j
public class BaseController {

    /**
     * 采用注解方式统一处理服务器未捕获异常
     */
    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return failure("系统异常");
    }

    protected ResultDTO success(String msg) {
        return new ResultDTO<>(true, null, msg);
    }

    protected <T> ResultDTO success(T data) {
        return new ResultDTO<>(true, data, "操作成功");
    }

    protected <T> ResultDTO success(String msg, T data) {
        return new ResultDTO<>(true, data, msg);
    }

    protected ResultDTO failure(String msg) {
        return new ResultDTO<>(false, null, msg);
    }
}
