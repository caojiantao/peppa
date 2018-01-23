package com.cjt.admin.interceptor;

import com.cjt.admin.annotation.Permissions;
import com.cjt.entity.admin.security.Role;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色拦截器，拦截controller层有权限注解的方法
 *
 * @author caojiantao
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        // 判断此处是否是方法处理器
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            // 判断方法上是否有该注解
            if (method.isAnnotationPresent(Permissions.class)) {
                Permissions permissions = method.getAnnotation(Permissions.class);
                String[] permissionRoleNames = permissions.value();
                User user = (User) httpServletRequest.getAttribute("user");
                List<Role> roles = user.getRoles();
                if (roles != null && !roles.isEmpty()) {
                    List<String> userRoleNames = new ArrayList<>();
                    for (Role role : roles) {
                        userRoleNames.add(role.getName());
                    }
                    for (String roleName : permissionRoleNames) {
                        if (userRoleNames.contains(roleName)) {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        // 返回false代表拦截，不会进行后续处理
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
