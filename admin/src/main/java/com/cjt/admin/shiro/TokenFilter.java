package com.cjt.admin.shiro;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 无状态过滤器，替代默认的authc
 * @author caojiantao
 */
public class TokenFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        redirectToLogin(request, response);
        return false;
    }
}
