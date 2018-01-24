package com.cjt.admin.controller;

import com.cjt.common.util.ExceptionUtil;
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

    public HttpServletRequest request;

    public HttpServletResponse response;

    /**
     * 该注释表示每个该类及子类的请求调用之前都回执行该方法
     */
    @ModelAttribute
    public void initReqResSession(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 采用注解方式统一处理服务器未捕获异常（500：服务器内部错误）
     */
    @ExceptionHandler
    public void handleException(HttpServletRequest request, Exception ex) throws IOException {
        logger.error(ExceptionUtil.toDetailStr(ex));
        // 注意setStatus和sendError的区别
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
