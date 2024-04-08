package com.study.jvm.actual_combat.memory_leak.demo4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存泄露问题：内部类引用外部类情况2
 * 匿名内部类对象如果在非静态方法中被创建，会持有调用者对象，垃圾回收时无法回收调用者。
 */
public class Outer {
    private byte[] bytes = new byte[1024 * 1024]; // 1M
    public List<String> newList() {
        // 使用匿名内部类的方式创建了数组对象，并将其返回
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
        }};
        return list;
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
        int count = 0;
        ArrayList<Object> objects = new ArrayList<>();
        while (true) {
            System.out.println(++count);
            /*
                将通过匿名内部类创建的对象放入到objects数组中，但是运行过程中发现内存中有Outer对象，但它是不需要的，所以造成了内存泄漏。
                此处虽然无法拿到Outer对象，但是他在内存中占着空间
             */
            objects.add(new Outer().newList());
        }
    }
}
