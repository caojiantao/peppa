package com.cjt.admin.shiro;


import com.cjt.common.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caojiantao
 */
public class CookieManager extends CookieRememberMeManager {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public void onSuccessfulLogin(Subject subject, AuthenticationToken token, AuthenticationInfo info) {
        //always clear any previous identity:
        forgetIdentity(subject);

        // 登录成功无论记住与否都得保存token，这就是无状态
        boolean remember = isRememberMe(token);
        Cookie cookie = getCookie();
        int maxAge = cookie.getMaxAge();
        if (!remember){
            cookie.setMaxAge(-1);
        }
        setCookie(cookie);
        rememberIdentity(subject, token, info);
        // 还原会话有效期
        cookie.setMaxAge(maxAge);
        setCookie(cookie);
    }

    /**
     * 需要重写此方法，依据JWT规范写入token
     */
    @Override
    public void rememberIdentity(Subject subject, AuthenticationToken token, AuthenticationInfo authcInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", subject.getPrincipal());
        String value = tokenUtil.getToken(map);
        Cookie cookie = new SimpleCookie(getCookie());
        cookie.setValue(value);
        cookie.saveTo(WebUtils.getHttpRequest(subject), WebUtils.getHttpResponse(subject));
    }
}
