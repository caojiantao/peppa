package com.cjt.admin.controller;

import com.cjt.entity.model.security.User;
import com.cjt.service.TokenService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

    private final IUserService userService;

    private final TokenService tokenService;

    @Value("${token_secret}")
    private String tokenSecret;

    @Autowired
    public SessionController(IUserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

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
