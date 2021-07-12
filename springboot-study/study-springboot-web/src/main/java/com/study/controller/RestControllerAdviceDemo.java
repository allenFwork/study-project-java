package com.study.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器: 拦截异常
 * @ControllerAdvice 用来拦截所有的 Controller
 * @RestControllerAdvice 就是返回时转换为了json对象 等价于 @ControllerAdvice + @ResponseBody
 */
@RestControllerAdvice(basePackages = "com.study.controller")
public class RestControllerAdviceDemo {

    /**
     * 通过 @ExceptionHandler 来捕获异常
     * 在spring boot中该捕获异常方法不生效
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object pageNotFound(HttpServletRequest request, HttpStatus httpStatus, Throwable throwable) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("statusCode", request.getAttribute("javax.servlet.error.status_code"));
        errors.put("requestUri", request.getAttribute("javax.servlet.error.request_uri"));
        return errors;
    }

    @ExceptionHandler(NullPointerException.class)
    public Object nullPointException(Throwable throwable) {
        /**
         * 不能用request获取信息，会出现问题，上面那个可能就是这个原因
         */
        return throwable.getMessage();
    }

}
