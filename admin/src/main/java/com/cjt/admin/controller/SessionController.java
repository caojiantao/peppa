package com.cjt.admin.controller;

import com.cjt.entity.admin.security.User;
import com.cjt.service.security.IUserService;
import com.cjt.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    @PostMapping(value = {"", "/"})
    private Object login(String username, String password, boolean rememberMe) {
        User user = userService.login(username, password);
        if (user != null) {
            String token = tokenService.getToken(username, rememberMe);
            Map<String, Object> resultMap = new HashMap<>(2);
            resultMap.put("token", token);
            resultMap.put("userId", user.getId());
            return resultMap;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "用户名或密码错误！";
        }
    }
}
