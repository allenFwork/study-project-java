# 初识 JVM

## 1. 什么是 JVM

<font color=red>JVM</font> 全称是 Java Virtual Machine，中文译名 <font color=red>Java虚拟机</font>。

<font color=red>JVM</font> 本质上是一个运行在计算机上的程序，他的职责是运行<font color=red>Java字节码文件</font>。

![image-20240105151758073](images\images-1.X\image-20240105151758073.png)

## 2. JVM的功能

![image-20240105152121928](images\images-1.X\image-20240105152121928.png)

### 2.1 解释和运行

- 对字节码文件中的指令，实时的解释成机器码，让计算机执行

### 2.2 内存管理

- 自动为对象、方法等分配内存
- 自动的垃圾回收机制，回收不再使用的对象

### 2.3 即时编译

- 对热点代码进行优化，提升执行效率
1. Java语言如果不做任何优化，性能不如C、C++等语言。
   
   ![image-20240105152604485](images\images-1.X\image-20240105152604485.png)

2. Java需要实时解释，主要是为了支持跨平台特性。
   
   ![image-20240105152638585](images\images-1.X\image-20240105152638585.png)

3. 由于JVM需要实时解释虚拟机指令，不做任何优化性能不如直接运行机器码的C、C++等语言。
   
   ![image-20240105152747478](images\images-1.X\image-20240105152747478.png)

4. JVM提供了<font color=red>即时编译（Just-In-Time 简称JIT) </font>进行性能的优化，最终能达到接近C、C++语言的运行性能甚至在特定场景下实现超越。
   
   ![image-20240105171019807](images\images-1.X\image-20240105171019807.png)

## 3. 常见的JVM

![image-20240105171203383](images\images-1.X\image-20240105171203383.png)

### 3.1 Java虚拟机规范

- 《Java虚拟机规范》由Oracle制定，内容主要包含了Java虚拟机在设计和实现时需要遵守的规范，主要包含class字节码文件的定义、类和接口的加载和初始化、指令集等内容

- 《Java虚拟机规范》是对虚拟机设计的要求，而不是对Java设计的要求，也就是说虚拟机可以运行在其他的语言，比如Groovy、Scala生成的class字节码文件之上。

- 官网地址：https://docs.oracle.com/javase/specs/index.html

### 3.2 HotSpot的发展历程

![image-20240105171509487](images\images-1.X\image-20240105171509487.png)

## 4. 总结

1. JVM到底是什么?
- JVM 全称是  Java  Virtual Machine，中文译名 Java虚拟机，是一个运行在计算机上的程序，他的职责是运行Java字节码文件。
2. JVM的三大核心功能是什么？
- JVM 包含 
- 理、解释执行虚拟机指令、即时编译三大功能。
3. 常见的JVM虚拟机有哪些？
- 常见的JVM有HotSpot、GraalVM、OpenJ9等，另外DragonWell龙井JDK也提供了一款功能增强版的JVM。其中使用最广泛的是HotSpot虚拟机。
