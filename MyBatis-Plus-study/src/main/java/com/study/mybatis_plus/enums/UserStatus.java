package com.study.mybatis_plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL(1, "正常"),
    FREEZE(2, "冻结"),
    ;
    @EnumValue // 该注解用来告诉 MyBatis Plus 框架，该枚举哪个值用来与数据库中表字段匹配
    private final int value;
    /*
     * 返回给前端的数据是由spring mvc封装的，spring mvc底层处理JSON数据使用了fasterxml.jackson，
     * 所以可以使用 fasterxml.jackson  的@JsonValue 注解，判断该枚举类型返回什么值作为json对象的值
     */
    @JsonValue
    private final String desc;

    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
