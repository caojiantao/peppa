package com.cjt.admin.shiro;


import com.cjt.common.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
        rememberIdentity(subject, token, info);
    }

    /**
     * 需要重写此方法，依据JWT规范写入token
     */
    @Override
    public void rememberIdentity(Subject subject, AuthenticationToken token, AuthenticationInfo authcInfo) {
        Cookie cookie = new SimpleCookie(getCookie());
        Map<String, Object> map = new HashMap<>();
        // 未记住登录则设置cookie为会话级别
        if (((UsernamePasswordToken)token).isRememberMe()) {
            map.put("exp", System.currentTimeMillis() + cookie.getMaxAge());
        } else {
            cookie.setMaxAge(-1);
        }
        map.put("username", subject.getPrincipal());
        byte[] bytes = getSerializer().serialize(authcInfo.getPrincipals());
        map.put("principals_byte", bytes);
        String value = tokenUtil.getToken(map);
        cookie.setValue(value);
        cookie.saveTo(WebUtils.getHttpRequest(subject), WebUtils.getHttpResponse(subject));
    }

    @Override
    public PrincipalCollection getRememberedPrincipals(SubjectContext subjectContext) {
        ServletRequest request = ((WebSubjectContext) subjectContext).getServletRequest();
        ServletResponse response = ((WebSubjectContext) subjectContext).getServletResponse();
        SimpleCookie token = new SimpleCookie("token");
        String tokenStr = token.readValue((HttpServletRequest) request, (HttpServletResponse) response);
        Object base64 = tokenUtil.parseToken(tokenStr, "principals_byte");
        if (base64 == null) {
            return null;
        }
        return getSerializer().deserialize(Base64.decode(base64.toString()));
    }
}
