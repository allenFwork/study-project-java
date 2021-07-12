package com.study.exception;

/**
 * 自定义异常
 */
public class SpringException extends RuntimeException {

    public SpringException(String message) {
        super(message);
    }

}