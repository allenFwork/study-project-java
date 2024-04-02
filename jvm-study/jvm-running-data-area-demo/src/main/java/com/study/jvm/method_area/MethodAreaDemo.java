package com.study.jvm.method_area;

import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Opcodes;

import java.io.IOException;

/**
 * 方法区的溢出测试
 */
public class MethodAreaDemo extends ClassLoader {
    public static void main(String[] args) throws IOException {
//        System.in.read();
        MethodAreaDemo methodAreaDemo = new MethodAreaDemo();
        int count = 0;
        while (true) {
            String name = "Class" + count;
            ClassWriter classWriter = new ClassWriter(0);
            // 第一个参数：JDK版本(主版本和副版本号)；
            // 第三个参数：类的全限定名
            // 其余参数照着写，不能漏
            classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", null);
            // 拿到字节码数据
            byte[] bytes = classWriter.toByteArray();
            // 因为 MethodAreaDemo 继承了 ClassLoader，所以它是一个类加载器，通过它来加载上面获取的字节码文件数据
            methodAreaDemo.defineClass(name, bytes, 0, bytes.length);
            System.out.println(++count);
        }
    }
}
