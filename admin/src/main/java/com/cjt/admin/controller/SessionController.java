package com.cjt.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.model.security.User;
import com.cjt.service.TokenService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("")
    private Object login(String username, String password, boolean rememberMe) {
        User user = userService.login(username, password);
        if (user != null) {
            // 生成登录凭证token
            String token = tokenService.getToken(user.getId(), rememberMe);
            JSONObject object = new JSONObject();
            object.put("token", token);
            object.put("userId", user.getId());
            return success("登录成功", object);
        } else {
            return failure("用户名或密码错误");
        }
    }
}
