package com.cjt.admin.controller;

import com.cjt.service.IUserService;
import com.cjt.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Value("${token_secret}")
    private String tokenSecret;

    @Resource
    private TokenService tokenService;

    /**
     * 登录创建会话
     */
    @GetMapping(value = {"", "/"})
    private Object login(String username, String password, boolean rememberMe) {
        if (userService.login(username, password)){
            String token = tokenService.getToken(username, rememberMe);
            Map<String, String> resultMap = new HashMap<>(2);
            resultMap.put("token", token);
            resultMap.put("username", username);
            return resultMap;
        } else {
            return "用户名或密码错误！";
        }
    }
}
