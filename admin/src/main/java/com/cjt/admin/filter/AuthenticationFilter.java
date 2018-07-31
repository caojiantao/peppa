package com.cjt.admin.filter;

import com.alibaba.fastjson.JSON;
import com.caojiantao.common.util.CollectionUtils;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.service.TokenService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private TokenService tokenService;

    @Getter
    @Setter
    private String tokenName;

    @Getter
    @Setter
    private List<String> excludePaths;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setCors(httpResponse);
        // 请求过滤
        if (isExcluded(httpRequest) || isPreflight(httpRequest) || isValidRequest(httpRequest, httpResponse)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.getWriter().write(JSON.toJSONString(new ResultDTO<>(false, null, "无权访问")));
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
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
    }

    /**
     * 是否不进行处理
     */
    private boolean isExcluded(HttpServletRequest request) {
        if (CollectionUtils.isNotEmpty(excludePaths)) {
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

    /**
     * 是否为合法请求，具备有效认证头header，且当前账号权限正常
     */
    private boolean isValidRequest(HttpServletRequest request, HttpServletResponse response) {
        String tokenStr = request.getHeader(tokenName);
        int userId = tokenService.parseToken(tokenStr);
        if (userId != 0) {
            return true;
        } else {
            // 请求登录认证无效，更改状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
