//package com.study.interceptor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.lang.Nullable;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.InputStream;
//
///**
// * 1. Spring5 以后使用的是 java8，java8中接口添加了default的方法实现
// * 2. HandlerInterceptor接口在springboot web模块中，如果想要使用该接口作为拦截器，需要引入 web 模块的依赖
// * 3. 使用 UserControllerInterceptor 作为拦截器时，需要将其交由spring管理起来，只能通过以下方法：
// *
// *     继承了 WebMvcConfigurerAdapter类，重写添加拦截器的方法
// *     @Override
// *     public void addInterceptors(InterceptorRegistry registry) {
// *         registry.addInterceptor(new UserControllerInterceptor());
// *     }
// *
// */
//public class UserControllerInterceptor implements HandlerInterceptor {
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 将校验逻辑存放在这里
////        InputStream inputStream = request.getInputStream();
//        return true;
//    }
//
//    /**
//     * 处理完请求方法后，进行逻辑处理
//     */
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//                           @Nullable ModelAndView modelAndView) throws Exception {
//        Integer status = response.getStatus();
//        // 处理返回状态码
//        if (status == HttpStatus.BAD_REQUEST.value()) {
//            response.setStatus(HttpStatus.OK.value());
//        }
//    }
//
//}
