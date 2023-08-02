package com.study.redis.apply.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.study.redis.apply.dto.UserDTO;
import com.study.redis.apply.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.study.redis.apply.utils.RedisConstants.LOGIN_USER_KEY;
import static com.study.redis.apply.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * 刷新用户登录凭证缓存的拦截器
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StringUtils.isBlank(token)) { // 判断token是否为空，直接放行
            return true;
        }

        // 2.基于token获取redis中的用户
        // 通过 opsForHash().entries(String key) 方法直接获取对应key的所有数据,返回Map类型；
        // opsForHash().entries方法会对其进行空判断，如果是空的会返回一个空的Map
        // opsForHash().get(String key, Object field) 该方法只能获取key对应的其中一个Field的值
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(LOGIN_USER_KEY + token);
        if (userMap.isEmpty()) {
            return true; // 直接放行
        }

        // 3.将查询到的Hash数据转化为UserDTO对象
        // fillBeanWithMap第三个参数设置为false: 表示不会略错误，如果有异常，直接抛出来
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);

        // 4.存在，保存用户信息到ThreadLocal中（通过使用UserHolder工具类直接获取ThreadLocal对象）
        UserHolder.saveUser(userDTO);

        // 6.刷新token有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.SECONDS);

        // 5.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserHolder.removeUser();
    }

}
