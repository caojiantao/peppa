package com.cjt.admin.shiro;

import com.cjt.common.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.servlet.SimpleCookie;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无状态过滤器，替代默认的authc
 *
 * @author caojiantao
 */
public class TokenFilter extends UserFilter {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 解析token，如果解析失败直接拦截
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            SimpleCookie cookie = new SimpleCookie("token");
            cookie.readValue((HttpServletRequest) request, (HttpServletResponse) response);
            String username = tokenUtil.parseToken(cookie.getValue(), "username", String.class);
            return StringUtils.isNotBlank(username);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        redirectToLogin(request, response);
        return false;
    }
}
