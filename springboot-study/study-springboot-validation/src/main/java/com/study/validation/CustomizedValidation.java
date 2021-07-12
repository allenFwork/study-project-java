package com.study.validation;

import com.study.annotation.CustomizedConstraintAnnotation;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 实现
 */
public class CustomizedValidation implements ConstraintValidator<CustomizedConstraintAnnotation, String> {

    @Override
    public void initialize(CustomizedConstraintAnnotation constraintAnnotation) {

    }

    /**
     * 定义校验规则：
     *   通过员工的卡号来校验，需要通过功耗的前缀和后缀来判断，
     *   前缀必须是以“USER-”，后缀必须是数字，通过Bean Validation来校验
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        /*
         * 1. Apache commons-lang StringUtils
         *   delimitedListToStringArray 方法将一个 String 转换为字符串数组
         * 2. 为什么不使用 split 方法转换?
         *   原因在于该方法使用了正则表达式，其次split方法
         * 3. 如果在依赖中，没有 StringUtils.delimitedListToStringArray API的话，
         *    可以使用java.util.StringTokenizer类的方法
         */
        String[] parts = StringUtils.delimitedListToStringArray(value, "-");
        if (parts.length != 2) {
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isValidPrefix = Objects.equals(prefix, "USER");
        // 判断是不是数字
        boolean isValidSuffix = org.apache.commons.lang3.StringUtils.isNumeric(suffix);

        return isValidPrefix && isValidSuffix;
    }

}
