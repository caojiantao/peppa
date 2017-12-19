package com.cjt.admin.controller;

import com.cjt.common.util.TokenUtil;
import com.cjt.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    private Object login(String account, String password) {
        Map<String, Object> map = new HashMap<>();
        if (userService.existAccount(account)) {
            Long userId = userService.login(account, password);
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
        return map;
    }
}
