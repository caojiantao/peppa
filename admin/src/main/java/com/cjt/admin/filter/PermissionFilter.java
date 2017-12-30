package com.cjt.admin.filter;

import com.cjt.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限过滤器
 *
 * @author caojiantao
 */
public class PermissionFilter implements Filter {

    @Value("${token_name}")
    private String tokenName;

    @Value("${token_header_name}")
    private String tokenHeaderName;

    @Resource
    private TokenService tokenService;

    private List<String> excludePaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (isExcluded(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String tokenStr = "";
        Cookie cookies[] = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    tokenStr = cookie.getValue();
                    break;
                }
            }
        } else {
            tokenStr = httpRequest.getHeader(tokenHeaderName);
        }
        String username = tokenService.parseToken(tokenStr);
        if (StringUtils.isNotBlank(username)) {
            chain.doFilter(request, response);
        } else {
            String errorMsg = "未登录";
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.getWriter().write(errorMsg);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isExcluded(HttpServletRequest request) {
        if (excludePaths != null && !excludePaths.isEmpty()) {
            String requestUrl = request.getRequestURI();
            PathMatcher matcher = new AntPathMatcher();
            for (String excludePath : excludePaths) {
                // 利用spring提供的PathMatch进行匹配
                if (matcher.match(excludePath, requestUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }
}
