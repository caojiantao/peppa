package com.cjt.admin.controller;

import com.cjt.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

    @Resource
    private IUserService userService;

    /**
     * 登录创建会话
     */
    @PostMapping("")
    private Object login(String username, String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
        } catch (UnknownAccountException e1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return "该账号不存在";
        } catch (IncorrectCredentialsException e2) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "密码不正确";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "未知错误";
        }
        return "登录成功";


        /*Map<String, Object> map = new HashMap<>();
        if (userService.existAccount(username)) {
            Long userId = userService.login(username, password);
            if (userId != null) {
                response.setStatus(HttpStatus.OK.value());
                // 用户登录成功采用jwt生成token
                map.put("token", TokenUtil.getToken(userId));
                map.put("userId", userId);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return "密码不正确";
            }
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return "该账号不存在";
        }
        return map;*/
    }
}
