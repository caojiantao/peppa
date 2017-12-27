package com.cjt.admin.shiro;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;

/**
 * @author caojiantao
 */
public class CookieManager extends CookieRememberMeManager {

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
}
