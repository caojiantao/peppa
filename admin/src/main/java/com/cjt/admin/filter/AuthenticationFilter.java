package com.cjt.admin.filter;

import com.cjt.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录鉴权
 *
 * @author caojiantao
 */
public class AuthenticationFilter implements Filter {

    private static final String TOKEN_HEADER_NAME = "X-Token";

    private String tokenName = TOKEN_HEADER_NAME;

    @Autowired
    private TokenService tokenService;

    private List<String> excludePaths;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setCors(httpResponse);
        if (isExcluded(httpRequest) || isPreflight(httpRequest) || isValidRequest(httpRequest)) {
            chain.doFilter(request, response);
        } else {
            String errorMsg = "无权访问";
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.getWriter().write(errorMsg);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 根绝CORE规范设置跨域资源共享
     */
    private void setCors(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "X-Token");
    }

    /**
     * 是否不进行处理
     */
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

    /**
     * 是否为AJAX预请求
     */
    private boolean isPreflight(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isValidRequest(HttpServletRequest request) {
        String tokenStr = request.getHeader(tokenName);
        String username = tokenService.parseToken(tokenStr);
        return StringUtils.isNotBlank(username);
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
