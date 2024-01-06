# 字节码文件详解

# 1. Java虚拟机的组成 

![image-20240106085027621](images\image-20240106085027621.png)



# 2. 字节码文件的组成 

## 2.1 应用场景

1. 解决面试难题

   <img src="images\image-20240106085532286.png" alt="image-20240106085532286" style="zoom:50%;" />

2. 解决工作中的实际问题：版本冲突问题

   <img src="images\image-20240106085601818.png" style="zoom: 67%;" />

3. 解决工作中的实际问题：系统升级

   <img src="images\image-20240106085717019.png" alt="image-20240106085717019" style="zoom:67%;" />



## 2.2 学习路线

![image-20240106085815420](images\image-20240106085815420.png)

![image-20240106085855927](images\image-20240106085855927.png)

![image-20240106085942982](images\image-20240106085942982.png)

![image-20240106090020554](images\image-20240106090020554.png)



### 2.2.1 以正确的姿势打开文件

- 字节码文件中保存了源代码编译之后的内容，以二进制的方式存储，无法直接用记事本打开阅读。 

- 通过NotePad++使用十六进制插件查看class文件：

  ![image-20240106090306720](images\image-20240106090306720.png)



- 推荐使用<font color=red> jclasslib</font>工具查看字节码文件。

- Github地址： https://github.com/ingokegel/jclasslib 

  ![image-20240106090507873](images\image-20240106090507873.png)



#### 2.2.1.1 案例代码（修改后版本）

```java
package com.study.jvm.demo1;

public interface SimpleInterface {
    public void interfaceMethodDemo();
}


package com.study.jvm.demo1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SimpleClass implements SimpleInterface {

    private final static int a1 = 0;
    private int a2 = 0;

    public static void main(String[] args) {

        Class<SimpleClass> clazz = SimpleClass.class;
        // 获取方法信息
        Method[] methods = clazz.getMethods();
        // 获取字段信息
        Field[] fields = clazz.getFields();

        String str1 = "123";
        String str2 = "123";
        int a = 1;
        System.out.println("123");
    }

    @Override
    public void interfaceMethodDemo() {
        System.out.println("重写接口方法测试");
    }
}
```



#### 2.2.1.2 jclasslib 的使用

1. 查看字节码文件的基本信息

   ![image-20240106090707755](images\image-20240106090707755.png)

2. 查看字节码文件的常量池

   ![image-20240106093011090](images\image-20240106093011090.png)

3. 查看字节码文件的字段信息

   ![image-20240106093113970](images\image-20240106093113970.png)

4. 选择方法中对应的方法名，查看字节码文件的 <font color=red>字节码指令</font>

   ![image-20240106093323375](images\image-20240106093323375.png)

   <img src="images\image-20240106093401202.png" alt="image-20240106093401202" style="zoom: 50%;" />

5. 查看字节码文件的属性：类的属性，不是字段信息，是例如源码文件名、内部类的列表等信息

   ![image-20240106093454135](images\image-20240106093454135.png)

#### 2.2.1.3 Idea中安装jclasslib的插件使用

1. 安装插件

   ![image-20240106102933097](images\image-20240106102933097.png)

2. 单击选中对应的类文件（也可以是对应的class文件），点击View选框，选择 Show Bytecode With Jclasslib

   <img src="images\image-20240106103345769.png" alt="image-20240106103345769" style="zoom:50%;" />

3. 右侧弹出对应的视图，查看相应信息

   ![image-20240106103510738](images\image-20240106103510738.png)



### 2.2.2 字节码文件的组成

<img src="images\image-20240106103615716.png" alt="image-20240106103615716" style="zoom:67%;" />

#### 2.2.2.1 Magic 魔数

![image-20240106103954885](images\image-20240106103954885.png)

- 文件是<font color=red>无法通过文件扩展名</font>来确定文件类型的，文件扩展名可以随意修改，不影响文件的内容。

- <font color=red>软件使用文件的头几个字节（文件头）去校验文件的类型，如果软件不支持该种类型就会出错。</font>

- Java字节码文件中，将文件头称为<font color=red>magic魔数。</font>

  ![image-20240106104215244](images\image-20240106104215244.png)

#### 2.2.2.2 主副版本号

- <font color=red>主副版本号指的是编译字节码文件的JDK版本号，</font>主版本号用来标识大版本号。

  - JDK1.0 - 1.1 使用了45.0 - 45.3，JDK1.2 是46之后每升级一个大版本就加1；
  - 副版本号是当主版本号相同时，作为区分不同版本的标识，一般只需要关心主版本号。

- <font color=red>版本号的作用主要是判断当前字节码的版本和运行时的JDK是否兼容。</font>

  ![image-20240106105343965](images\image-20240106105343965.png)

##### 主版本号不兼容导致的错误

**需求：**解决以下由于主版本号不兼容导致的错误

![image-20240106105615039](images\image-20240106105615039.png)

**两种方案：** 

1. 升级JDK版本 <font color=red>（容易引发其他的兼容性问题，并且需要大量的测试） </font>

2. 将第三方依赖的版本号降低或者更换依赖，以满足JDK版本的要求   <font color=red>√ 建议采用</font>

#### 2.2.2.3 基础信息

![image-20240106110124710](images\image-20240106110124710.png)

#### 2.2.2.4 常量池

- 字节码文件中常量池的作用：避免相同的内容重复定义，节省空间。

  ![image-20240106110324063](images\image-20240106110324063.png)

- 常量池中的数据都有一个编号，编号从1开始。在字段或者字节码指令中通过编号可以快速的找到对应的数据。

- 字节码指令中通过编号引用到常量池的过程称之为<font color=red>符号引用。</font>

  ![image-20240106110528874](images\image-20240106110528874.png)



#### 2.2.2.5 方法

![image-20240106110948494](images\image-20240106110948494.png)

- 字节码中的方法区域是存放<font color=red>字节码指令</font>的核心位置，字节码指令的内容存放在方法的Code属性中。

  ![image-20240106111107942](images\image-20240106111107942.png)

- 操作数栈是临时存放数据的地方，局部变量表是存放方法中的局部变量的位置。

##### i = i + 1执行流程分析

![image-20240106120351865](images\image-20240106120351865.png)

![image-20240106120600218](images\image-20240106120600218.png)

![image-20240106120956610](images\image-20240106120956610.png)

##### i = i++ 执行流程分析

![image-20240106121158407](images\image-20240106121158407.png)

##### i = ++i 执行流程分析

![image-20240106121545168](images\image-20240106121545168.png)

##### 总结

![image-20240106121622019](images\image-20240106121622019.png)

##### 练习：查看字节码文件并解答问题

**问题：** 通过字节码指令分析下面三种 “加一” 的操作性能的高低? 

```java
int i = 0, j = 0,  k = 0;
i++;         // 解释为执行 1 条指令
j = j + 1;   // 解释为执行 4 条指令
k += 1;      // 解释为执行 1 条指令
```

![image-20240106133706222](images\image-20240106133706222.png)

![image-20240106133757911](images\image-20240106133757911.png)



### 2.2.3 玩转字节码常用工具

#### 2.2.3.1 javap-v命令

- javap 是 JDK 自带的反编译工具，可以通过控制台查看字节码文件的内容。<font color=red>适合在服务器上查看字节码文件内容。</font>
- 直接输入javap查看所有参数。
- 输入 <font color=red>javap -v 字节码文件名称 </font> 查看具体的字节码信息。（如果jar包需要先使用 jar –xvf 命令解压）
- ![](images\image-20240106135253105.png)

<img src="images\image-20240106135708286.png" alt="image-20240106135708286" style="zoom:50%;" />

![](images\image-20240106140024494.png)

#### 2.2.3.2 jclasslib插件

[Idea中安装jclasslib的插件使用]: 

#### 2.2.3.3 阿里arthas

- Arthas 是一款线上监控诊断产品，通过全局视角实时查看应用 load、内存、gc、线程的状态信息，并能在不修改应用代码的情况下，对业务问题进行诊断，大大提升线上问题排查效率。
- 官网：https://arthas.aliyun.com/doc/ （通过官网查看相应的命令作用）
- dump 类的全限定名：dump已加载类的字节码文件到特定目录。 
- jad 类的全限定名：反编译已加载类的源码。
- ![](images\image-20240106141821249.png)



##### 环境准备

1. 启动一个 java程序，此处执行 MainTest.java 的 main方法

   ```
   package com.study.jvm.demo3;
   
   import java.io.IOException;
   
   // 测试内存情况的程序类
   public class MainTest {
   
       private static final int i1 = 1;
       private static int i2;
       private int i3;
       private static final String str1 = "str1";
       private static String str2;
       private String str3;
   
       public static void main(String[] args) throws IOException {
           i2 = 2;
           str2 = "str2";
   
           // 让程序一直跑着
           System.in.read();
       }
   
   }
   ```

2. 通过java指令执行字节码文件中main方法，会出现 **错误: 找不到或无法加载主类 MainTest** 的错误。

- 原因：jdk 指定的 classpath 中没有当前目录，所以即使当前目录下有该字节码文件，也找不到

- 解决办法：

  - windows下：`java -classpath class文件的存放路径（包级别上一层） class文件的全名 `

    ![image-20240106144742407](images\image-20240106144742407.png)

  - Linux下：`java -classpath /usr/local/apps/java_apps com.study.jvm.demo3.MainTest`

    ![](images\image-20240106145816946.png)

##### 启动 arthas

- 执行 <font color=red>`java -jar arthas-boot.jar`</font> 命令

  ![](images\image-20240106151619677.png)

  - 从上面日志可知 MainTest 程序的进程id 是 11857

##### 通过 <font color=red>dump命令 将正在运行的字节码文件保存下来</font>

- 命令：`dump -d 生成文件的地址 文件的全限名 `

  ![执行dump命令生成对应的字节码文件](images\image-20240106152742602.png)

  ![生成的结果在红框中的目录下](images\image-20240106153112193.png)

##### 通过 <font color=red>jad命令 反编译加载类的源码 </font>

- 命令：`jad com.study.jvm.demo3.MainTest `

  ![image-20240106153604754](images\image-20240106153604754.png)	

##### 通过 <font color=red>dashboard命令 查看程序的运行情况 (内存、CPU使用等信息) </font>

- 命令：`dashboard -i 2000 -n 1 `  每隔两秒打印一次结果，总共打印1次

  ![image-20240106152257465](images\image-20240106152257465.png)



##### 案例：使用阿里arthas定位线上出现的字节码问题

**背景：** 

小李的团队昨天对系统进行了升级修复了某个bug，但是升级完之后发现bug还是存在，小李怀疑是因为没有把最新的字节码文件部署到服务器上，请使用阿里的arthas去确认升级完的字节码文件是不是最新的。 

**思路：** 

1. 在出问题的服务器上部署一个arthas，并启动。 
2. 连接arthas的控制台，使用 jad 命令加上想要查看的类名，反编译出源码。
3. 确认源码是否是最新的。



### 2.2.4 总结

#### 1) 如何查看字节码文件？

- **本地文件可以使用 jclasslib工具查看，开发环境使用 jclasslib 插件。**

  <img src="images\image-20240106142710617.png" alt="image-20240106142710617" style="zoom:67%;" />

- **服务器上文件使用 javap 命令直接查看，也可以通过 arthas 的 dump 命令导出字节码文件，再查看本地文件。还可以使用 jad 命令反编译出源代码。**

  ![image-20240106142815841](images\image-20240106142815841.png)	

  

#### 2) 字节码文件的核心组成有哪些？

<img src="images\image-20240106155118203.png" alt="image-20240106155118203" style="zoom:67%;" />



# 3  类的生命周期 





# 4 类加载器

