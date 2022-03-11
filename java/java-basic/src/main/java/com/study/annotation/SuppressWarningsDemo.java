package com.study.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * 简介：java.lang.SuppressWarnings是J2SE5.0中标准的Annotation之一。可以标注在类、字段、方法、参数、构造方法，以及局部变量上。
 * 作用：告诉编译器忽略指定的警告，不用在编译完成后出现警告信息。
 * 使用：
 * @SuppressWarnings(“”)
 * @SuppressWarnings({})
 * @SuppressWarnings(value={})
 * 根据sun的官方文档描述：
 * value -将由编译器在注释的元素中取消显示的警告集。允许使用重复的名称。忽略第二个和后面出现的名称。
 *       出现未被识别的警告名不是错误：编译器必须忽略无法识别的所有警告名。
 *       但如果某个注释包含未被识别的警告名，那么编译器可以随意发出一个警告。
 *
 * 各编译器供应商应该将它们所支持的警告名连同注释类型一起记录。鼓励各供应商之间相互合作，确保在多个编译器中使用相同的名称。
 */
public class SuppressWarningsDemo {

    // 添加方法上面,可以使编译器不报方法中所有的 “未使用变量” 的错误
//    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // 添加在变量a上面,可以使编译器不报 “未使用变量a” 的错误
        @SuppressWarnings("unused")
        int a = 1;
        int b = 2;
        System.out.println(1);
    }

    // 告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息。
    @SuppressWarnings("unchecked")
    public void addItems(String item){
        // 编译器报错：1.item参数未被使用 2.item被修改为集合
        List items = new ArrayList();
        // 编译器会报错：添加的元素类型不一定匹配(通过方法上的unchecked被去除掉了)
        items.add(item);
    }

}
