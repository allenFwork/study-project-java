package com.study.jvm.java_agent.command;

import com.study.jvm.java_agent.enhancer.AsmEnhancer;
import com.study.jvm.java_agent.enhancer.MyAdvice;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.jd.core.v1.ClassFileToJavaSourceDecompiler;
import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.loader.LoaderException;
import org.jd.core.v1.api.printer.Printer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClassCommand {

    // 打印所有类加载器
    public static void printAllClassLoader(Instrumentation inst) {
        HashSet<ClassLoader> classLoaders = new HashSet<>();
        // 获取所有类
        Class[] allLoadedClasses = inst.getAllLoadedClasses();

        for (Class allLoadedClass : allLoadedClasses) {
            ClassLoader classLoader = allLoadedClass.getClassLoader();
            classLoaders.add(classLoader);
        }

        // 打印类加载器
        String str = classLoaders.stream().map(x -> {
            // 如果为null，那么它就是启动类加载器
            if (x == null) {
                return "BootStrapClassLoader";
            } else {
                // return x.toString();
                return x.getName();
            }
        }).filter(x -> x != null).distinct().sorted(String::compareTo).collect(Collectors.joining(",")); //去重，排序，通过“,”连接起来组成字符串

        System.out.println(str);
    }


    // 打印类的源代码
    public static void printClassSourceCode(Instrumentation inst) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入类名:");
        String className = scanner.next();
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        System.out.println("要查找的类名是:" + className);

        // 根据类名找到Class对象
        for (Class clazz : allLoadedClasses) {
            if (clazz.getName().equals(className)) {
                System.out.println("找到了类, 类加载器为: " + clazz.getClassLoader());

                // 编写转换器:这个转换器的作用是获取当前类的字节码信息
                ClassFileTransformer transformer = new ClassFileTransformer() {
                    @Override
                    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
                        ClassFileToJavaSourceDecompiler classFileToJavaSourceDecompiler = new ClassFileToJavaSourceDecompiler();
                        // System.out.println("字节码信息：" + classFileBuffer);
                        // 通过jd-core反编译，并且打印源代码
                        try {
                            printJDCoreSourceCode(classFileBuffer, className);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return ClassFileTransformer.super.transform(module, loader, className, classBeingRedefined, protectionDomain, classFileBuffer);
                    }
                };

                // 1.添加转换器，第二个参数表示能不能进行转换，默认是不能的，如果是false,那么就无法进行手动触发转换器了
                inst.addTransformer(transformer, true);

                // 2.手动触发转换器
                try {
                    inst.retransformClasses(clazz);
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                } finally {
                    // 3.删除转换器
                    inst.removeTransformer(transformer);
                }

            }
        }
    }

    // 通过jd-core打印类的源代码(jd-core的使用可以参考github上的使用说明)
    private static void printJDCoreSourceCode(byte[] bytes, String className) throws Exception {
        // 1.创建jd-core的Loader对象
        Loader loader = new Loader() {
            @Override
            public boolean canLoad(String s) {
                return true; // 代表这个类可以被加载
            }

            @Override
            public byte[] load(String s) throws LoaderException {
                return bytes; // 直接返回该类的字节码数组
            }
        };

        // 2.创建jd-core的Printer对象
        Printer printer = new Printer() {
            protected static final String TAB = "  ";
            protected static final String NEWLINE = "\n";

            protected int indentationCount = 0;
            protected StringBuilder sb = new StringBuilder();

            @Override
            public String toString() {
                return sb.toString();
            }

            @Override
            public void start(int maxLineNumber, int majorVersion, int minorVersion) {
            }

            @Override
            public void end() {
                // 打印源代码
                System.out.println(sb.toString());
            }

            @Override
            public void printText(String text) {
                sb.append(text);
            }

            @Override
            public void printNumericConstant(String constant) {
                sb.append(constant);
            }

            @Override
            public void printStringConstant(String constant, String ownerInternalName) {
                sb.append(constant);
            }

            @Override
            public void printKeyword(String keyword) {
                sb.append(keyword);
            }

            @Override
            public void printDeclaration(int type, String internalTypeName, String name, String descriptor) {
                sb.append(name);
            }

            @Override
            public void printReference(int type, String internalTypeName, String name, String descriptor, String ownerInternalName) {
                sb.append(name);
            }

            @Override
            public void indent() {
                this.indentationCount++;
            }

            @Override
            public void unindent() {
                this.indentationCount--;
            }

            @Override
            public void startLine(int lineNumber) {
                for (int i = 0; i < indentationCount; i++) sb.append(TAB);
            }

            @Override
            public void endLine() {
                sb.append(NEWLINE);
            }

            @Override
            public void extraLine(int count) {
                while (count-- > 0) sb.append(NEWLINE);
            }

            @Override
            public void startMarker(int type) {
            }

            @Override
            public void endMarker(int type) {
            }
        };

        // 3.通过jd-core方法打印
        ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
        decompiler.decompile(loader, printer, className);
    }

    // 对类进行增强，统计执行耗时情况
    public static void enhanceClass(Instrumentation inst) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入类名:");
        String className = scanner.next();
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        System.out.println("要查找的类名是:" + className);

        // 根据类名找到Class对象
        for (Class clazz : allLoadedClasses) {
            if (clazz.getName().equals(className)) {
                System.out.println("找到了类, 类加载器为: " + clazz.getClassLoader());

//                // 通过ASM框架进行修改字节码文件（方法一）
//                // 编写转换器:这个转换器的作用是获取当前类的字节码信息
//                ClassFileTransformer transformer = new ClassFileTransformer() {
//                    @Override
//                    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
//                        // 通过ASM对类进行增强，返回字节码信息
//                        byte[] bytes = AsmEnhancer.enhanceClass(classFileBuffer);
//                        return bytes;
//                    }
//                };
//
//                // 1.添加转换器，第二个参数表示能不能进行转换，默认是不能的，如果是false,那么就无法进行手动触发转换器了
//                inst.addTransformer(transformer, true);
//
//                // 2.手动触发转换器
//                try {
//                    inst.retransformClasses(clazz);
//                } catch (UnmodifiableClassException e) {
//                    e.printStackTrace();
//                } finally {
//                    // 3.删除转换器
//                    inst.removeTransformer(transformer);
//                }

                // 使用bytebuddy框架修改字节码文件（方法二）
                new AgentBuilder.Default()
                        // 禁止byte buddy处理时，修改类名
                        .disableClassFormatChanges()
                        // 处理时，使用retransform增强
                        .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                        // 打印错误日志
                        .with(new AgentBuilder.Listener.WithTransformationsOnly(AgentBuilder.Listener.StreamWriting.toSystemError()))
                        // 匹配哪些类
                        .type(ElementMatchers.named(className))
                        // 增强：使用MyAdvice定义的通知（包含了增强的逻辑），对所有的方法都进行了增强
                        .transform((builder, typeDescription, classLoader, module, protectionDomain) -> builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.any())))
                        // 将此处的设定放到java agent的Instrumentation对象中
                        .installOn(inst);
            }
        }
    }

}
