package com.cjt.admin.controller;

import com.cjt.common.util.ExceptionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author caojiantao
 */
public class BaseController {

    private Logger logger = LogManager.getLogger(BaseController.this);

    public HttpServletRequest request;

    public HttpServletResponse response;

    public HttpSession session;

    /**
     * 该注释表示每个该类及子类的请求调用之前都回执行该方法
     */
    @ModelAttribute
    public void initReqResSession(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 采用注解方式统一处理服务器未捕获异常（500：服务器内部错误）
     */
    @ExceptionHandler
    public void handleException(HttpServletRequest request, Exception ex) throws IOException {
        // 将错误具体信息打印日志，返回500状态码
        String errorMsg = ExceptionUtil.toDetailStr(ex);
        logger.error(errorMsg);
        // 注意setStatus和sendError的区别
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
