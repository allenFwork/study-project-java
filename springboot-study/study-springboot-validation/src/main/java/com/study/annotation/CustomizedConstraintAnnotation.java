package com.study.annotation;

import com.study.validation.CustomizedValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义的验证规则注解
 */
@Target({ElementType.FIELD})
/**
 * @Retention 表示编译器编译后，什么情况下有：
 *    SOURCE 源代码下有；
 *    CLASS 类文件下有，默认情况，就是CLASS，运行时是无法读到该注解的
 *    RUNTIME 运行时有,主要用在反射中
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint( validatedBy = {CustomizedValidation.class} )
public @interface CustomizedConstraintAnnotation {

    String message() default "{com.study.annotation.validation.invalid.card.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
