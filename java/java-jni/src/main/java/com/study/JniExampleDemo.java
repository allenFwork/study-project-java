package com.study;

/**
 * 知识点：
 * 1. 因为源码文件中中文注释,所以直接使用 javac JniExample.java 报错
 *    需要使用 javac -encoding utf-8 JniExample.java 进行
 *    当然也可以直接使用idea工具进行编译生成class文件，直接执行main方法
 *
 * 2. 对native方法进行操作:
 *   1) 生成.class文件
 *   2) 通过.class文件生成.h文件
 *      javah -jni -classpath C:\Users\86131\IdeaProjects\study-project-java\java\java-jni\target\classes -d C:\Users\86131\IdeaProjects\study-project-java\java\java-jni\src\main\c_resources com.study.JniExampleDemo
 *   3) 编写 C/C++ 的 .c文件: JniExampleDemo.c
 *   4) 通过C文件编译成.dll文件(windows的可执行文件) .so文件(Linux的可执行文件)
 *      gcc -c -I "C:\work\jdk\openlogic-openjdk-8u262-b10-win-64\include" -I "C:\work\jdk\openlogic-openjdk-8u262-b10-win-64\include\win32" C:\Users\86131\IdeaProjects\study-project-java\java\java-jni\src\main\c_resources\JniExampleDemo.c
 *   5) 将 JniExampleDemo.o 文件转化为 windows平台下的动态链接库
 *      gcc -Wl,--add-stdcall-alias -shared -o JniExampleDemo.dll JniExampleDemo.o
 *   6) 给虚拟机配置参数：
 *      -Djava.library.path=C:\Users\86131\IdeaProjects\study-project-java\java\java-jni\src\main\resources\lib
 */
public class JniExampleDemo {

    // 设置本地方法的声明(C或C++的方法)
    public native void set(int count);
    public native int get();

    static {
        // 载入动态链接库, 参数动态链接库的名称
        System.loadLibrary("JniExampleDemo");
        // 需要采用绝对路径配置文件信息
//        System.load("C:\\Users\\86131\\IdeaProjects\\study-project-java\\java\\java-jni\\target\\classes\\lib\\JniExampleDemo.dll");

    }

    public static void main(String[] args) {
        int result = new JniExampleDemo().get();
        System.out.println(result);
        new JniExampleDemo().set(1);
        result = new JniExampleDemo().get();
        System.out.println(result);
    }

}
