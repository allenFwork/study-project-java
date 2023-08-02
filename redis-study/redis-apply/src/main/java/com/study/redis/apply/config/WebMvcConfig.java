package com.study.redis.apply.config;

import com.study.redis.apply.interceptor.LoginInterceptor3;
import com.study.redis.apply.interceptor.RefreshTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器使其生效
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1.进行登录校验，拦截部分请求
        registry.addInterceptor(new LoginInterceptor3()) // 添加拦截器
                .excludePathPatterns(                   // 配置拦截器不拦截的请求路径路径
                        "/shop/**",      // 查看店铺信息请求
                        "/shop-type/**",
                        "/upload/**",
                        "/voucher/**",
                        "/blog/hot",
                        "/user/code",   // 获取校验码请求
                        "/user/login"   // 登录请求
                ).order(1);
        // 2.进行用登陆凭证刷新，拦截所有请求
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
                .addPathPatterns("/**") // 默认就是拦截所有请求，也可以通过addPathPatterns设置拦截的请求
                .order(0); // 通过order设置，数字越小，拦截器越靠前（默认所有的拦截器的order都是0，那么就按照添加的顺序执行）
    }

}
