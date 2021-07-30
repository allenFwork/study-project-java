package com.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Tommy
 * Created by Tommy on 2018/8/31
 **/
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "customized error message")
public class CustomizedException extends Exception {
}
