package com.cjt.admin.shiro;

import com.cjt.common.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${token_name}")
    private String tokenName;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 解析token，如果解析失败直接拦截
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            SimpleCookie token = new SimpleCookie(tokenName);
            // 首先从cookie取，取不到再从header取
            String tokenStr = token.readValue((HttpServletRequest) request, (HttpServletResponse) response);
            if (StringUtils.isBlank(tokenStr)){
                tokenStr = ((HttpServletRequest) request).getHeader("X-Token");
            }
            Object username = tokenUtil.parseToken(tokenStr, "username");
            return username != null && StringUtils.isNotBlank(username.toString());
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        redirectToLogin(request, response);
        return false;
    }
}
