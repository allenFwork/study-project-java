package com.study.redis.apply.interceptor;

import com.study.redis.apply.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验拦截器(版本3)
 */
public class LoginInterceptor3 implements HandlerInterceptor {

    // 校验用户信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断是否需要拦截（ThreadLocal中是否由用户）
        if (UserHolder.getUser() == null) {
            // 没有该用户，需要拦截
            response.setStatus(401);
            return false;
        }
        // 有用户，则放行
        return true;
    }

}
