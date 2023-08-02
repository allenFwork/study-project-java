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
 * 登录校验拦截器(版本2: 使用redis缓存登录凭证)
 */
public class LoginInterceptor2 implements HandlerInterceptor {

    // 拦截器对象不是由spring进行创建管理的，所以无法直接将 StringRedisTemplate 注入进来，可通过构造方法手动注入
    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor2(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 校验用户信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 将原有的session存储换为了redis存储
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StringUtils.isBlank(token)) { // 判断token是否为空
            response.setStatus(401);
            return false;
        }
        // 2.基于token获取redis中的用户
        // 通过 opsForHash().entries(String key) 方法直接获取对应key的所有数据,返回Map类型；
        // opsForHash().entries方法会对其进行空判断，如果是空的会返回一个空的Map
        // opsForHash().get(String key, Object field) 该方法只能获取key对应的其中一个Field的值
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(LOGIN_USER_KEY + token);
        // 3.判断用户是否存在
        if (userMap.isEmpty()) {
            // 4.不存在，拦截，返回 401状态码（未授权的意思）
            response.setStatus(401);
            // 拦截
            return false;
        }
        // 5.将查询到的Hash数据转化为UserDTO对象
        // fillBeanWithMap第三个参数设置为false: 表示不会略错误，如果有异常，直接抛出来
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);

        // 6.存在，保存用户信息到ThreadLocal中（通过使用UserHolder工具类直接获取ThreadLocal对象）
        UserHolder.saveUser(userDTO);

        // 7.刷新token在redis中的有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 8.放行
        return true;
    }

    // 校验完成后，调用接controller层逻辑，处理完成以后，销毁对应的用户信息，以防内存泄露问题
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
