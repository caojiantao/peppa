package com.cjt.admin.controller;

import com.cjt.entity.dto.ResultDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caojiantao
 */
public class BaseController {

    private Logger logger = LogManager.getLogger(BaseController.class);

    protected static final String NOT_FOUND = "暂无数据";

    protected static final String OPERATION_FAILED = "操作失败";

    /**
     * 采用注解方式统一处理服务器未捕获异常（500：服务器内部错误）
     */
    @ExceptionHandler
    public void handleException(Exception ex) {
        logger.error(ExceptionUtils.getStackTrace(ex));
    }

    protected ResultDTO success(String msg) {
        return new ResultDTO(true, msg);
    }

    protected ResultDTO failure(String msg) {
        return new ResultDTO(false, msg);
    }
}
