package com.study.structure.decorate;

import com.study.structure.decorate.decoeator.Salt;
import com.study.structure.decorate.decoeator.Sugar;
import com.study.structure.decorate.entity.MilkTea;
import org.junit.Test;

public class DecorateTest {

    @Test
    public void test() {

        // 奶茶 + 糖
        MilkTea milkTea = new MilkTea("奶茶");
        Sugar sugar = new Sugar(milkTea);
        System.out.println(sugar.getDescription());
        System.out.println(sugar.price());

        System.out.println("-----------------------------------------");

        // 奶茶加盐
        Salt salt = new Salt(milkTea);
        System.out.println(salt.getDescription());
        System.out.println(salt.price());


    }

}
