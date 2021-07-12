package com.study.entity;

import com.study.annotation.CustomizedConstraintAnnotation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class User {

    // @Max(value = 10000) 表示：id 的值最大为 10000
    @Max(value = 10000)
    private long id;

    // @NotNull 表示：name不能为空
    @NotNull
    private String name;

    // 卡号：USER-123456789
    @NotNull
    @CustomizedConstraintAnnotation//(message = "卡号必须以\"USER-\"开头，以数字结尾")
    private String cardNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
