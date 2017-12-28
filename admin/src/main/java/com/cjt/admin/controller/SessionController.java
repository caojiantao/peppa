package com.cjt.admin.controller;

import com.cjt.common.encrypt.PasswordUtil;
import com.cjt.common.util.ExceptionUtil;
import com.cjt.service.IUserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

    private static final Logger log = LogManager.getLogger(SessionController.class);

    @Resource
    private IUserService userService;

    @Resource
    private PasswordUtil passwordUtil;

    /**
     * 登录创建会话
     */
    @PostMapping("")
    private Object login(String username, String password, boolean rememberMe) {
        try {
            password = passwordUtil.encryptPassword(password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException e1) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "用户名或密码不正确";
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error(ExceptionUtil.toDetailStr(ex));
            return "未知错误";
        }
        return "登录成功";
    }
}
