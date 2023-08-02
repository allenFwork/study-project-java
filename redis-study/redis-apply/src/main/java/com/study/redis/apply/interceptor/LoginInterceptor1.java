package com.study.redis.apply.interceptor;

import com.study.redis.apply.dto.UserDTO;
import com.study.redis.apply.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录校验拦截器(版本1：使用Session进行缓存)
 */
public class LoginInterceptor1 implements HandlerInterceptor {

    // 校验用户信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.获取Session
        HttpSession session = request.getSession();
        // 2.获取session中的用户
        Object user = session.getAttribute("user");
        // 3.判断用户是否存在
        if (user == null) {
            // 4.不存在，拦截，返回 401状态码（未授权的意思）
            response.setStatus(401);
            // 拦截
            return false;
        }
        // 5.存在，保存用户信息到ThreadLocal中（通过使用UserHolder工具类直接获取ThreadLocal对象）
        UserHolder.saveUser((UserDTO) user);

        // 6.放行
        return true;
    }

    // 校验完成后，调用接controller层逻辑，处理完成以后，销毁对应的用户信息，以防内存泄露问题
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
