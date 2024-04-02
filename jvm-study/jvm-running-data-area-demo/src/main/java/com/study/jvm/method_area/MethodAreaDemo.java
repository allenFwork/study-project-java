package com.study.jvm.method_area;

import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Opcodes;

import java.io.IOException;

/**
 * 方法区的溢出测试
 */
public class MethodAreaDemo extends ClassLoader {
    public static void main(String[] args) throws IOException {
        System.in.read();
        MethodAreaDemo methodAreaDemo = new MethodAreaDemo();
        int count = 0;
        while (true) {
            String name = "Class" + count;
            ClassWriter classWriter = new ClassWriter(0);
            classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", null);
            byte[] bytes = classWriter.toByteArray();
            methodAreaDemo.defineClass(name, bytes, 0, bytes.length);
            System.out.println(++count);
        }
    }
}
