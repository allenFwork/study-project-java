package com.study.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnableUtil {

    String userName() default  "allen";

    int time() default 10;

}
