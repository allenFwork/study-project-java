package com.study.structure.proxy.dynamicProxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Description 自定义动态生成代理类：
 *              使用静态代理（聚合方式）动态地生成代理类，无论代理多少个类，硬盘上都只生成一个代理类的源文件
 *
 *              缺点：
 *              1. 增强功能的逻辑代码已经被写死在这个生成代理类的工具类中了，如果要实现别的代理逻辑，就需要写新的工具类
 *              2. 涉及到与硬盘的输入和输出，影响性能
 * @Version 1.0
 * @Author allenwork
 */
public class ProxyUtil {

//    Object[] objects = new Object[]{};
//    objects.add(1);这是错的

    /**
     * 思路：因为需要返回一个代理对象实例，所以需要这个实例对象所对应的class对象，
     *      因为需要class对象,所以需要jvm加载到.class的字节码文件，
     *      因为需要.class字节码文件，所以需要该字节码文件未编译的.java源文件
     *
     * .java文件的来源：
     *   1.在项目中新建一个java文件（不是动态的，不行）
     *   2.通过java的io生成一个文件到磁盘，这个文件就是.java文件（通过程序动态生成的，可以）
     *
     * 实现步骤:
     *    生成java文件的内容，就是字符串
     *    --> 将其通过io流创建对应的.java文件到磁盘
     *    --> 通过程序的编译器将.java文件编译成.class文件
     *    --> 通过程序的类加载器加载.class文件生成class对象
     *    --> 通过java的反射实例化class对象对应的对象
     * @return
     */
    public static Object generateProxyObject(Object target) {

        /*---------------------------------------------编写java文件(开始)-----------------------------------------------*/
        // 最终获取的代理对象
        Object proxyObject = null;
        // 换行
        String line = "\n";
        // 空格
        String tab = "\t";
        // 接口名字
        String interfaceName = target.getClass().getInterfaces()[0].getSimpleName();

        // 生成 .java文件 中的字符串内容
        String content;
        String packageContent = "package com.study.proxy.dynamicProxy.custom;" + line + line;
        String importContent  = "import " + target.getClass().getName() + ";" + line  +
                                "import " + target.getClass().getInterfaces()[0].getName() + ";" + line + line;
        String classContent   = "public class CustomerProxyObject implements " + target.getClass().getInterfaces()[0].getSimpleName() + "{" + line + line;
        String fieldContent   = tab + "private " + target.getClass().getInterfaces()[0].getSimpleName() + " target;" + line + line;
        String construContent = tab + "public CustomerProxyObject("+interfaceName+" target) {" + line +
                                tab + tab + "this.target = target;" + line +
                                tab + "}" + line + line;

        // 代理类中重写的方法
        String methodContent = "";
        // 代理类中调用目标类中的方法
        String targetMethodContent = "";
        // 获取目标接口中的所有方法
        Method[] mehods = target.getClass().getDeclaredMethods();
        for (Method method : mehods) {
            Class returnType = method.getReturnType();
            String returnTypeContent = returnType.getSimpleName();
            String methodName = method.getName();

            // 获取方法的参数类型组成的数组,例如方法为 f(String a,String b)，则args[]为 String.class String.class
            Class[] args = method.getParameterTypes();
            // 代理类中重写方法的参数
            String parametersContent = "";
            // 目标类中方法被调用
            String targetParametersContent = "";
            for (int i = 0; i < args.length; i++) {
                parametersContent += args[i].getSimpleName()  + " p" + i + ",";
                targetParametersContent += "p" + i + ",";
            }
            if (args != null && args.length != 0) {
                parametersContent = parametersContent.substring(0, parametersContent.length()-1);
                targetParametersContent = targetParametersContent.substring(0, targetParametersContent.length()-1);
            }
            targetMethodContent = methodName + "(" + targetParametersContent + ")";
            methodContent += tab + "public " + returnTypeContent + " " + methodName + "(" + parametersContent + ") {" + line +
                             tab + tab + "System.out.println(\"customer proxy : before ... \");" + line ;

            // 判断调用目标类的方法是否有返回值
            if( !"void".equals(returnTypeContent)) {
                methodContent +=  tab + tab + returnTypeContent +" object = target." + targetMethodContent + ";" + line +
                                  tab + tab + "System.out.println(\"customer proxy : after ... \");" + line +
                                  tab + tab + "return object;" + line +
                                  tab + "}" + line + line;
            } else {
                methodContent += tab + tab + "target." + targetMethodContent + ";" + line +
                                 tab + tab + "System.out.println(\"customer proxy : after ... \");" + line +
                                 tab + "}" + line + line;
            }

        }
        content = packageContent + importContent + classContent + fieldContent + construContent + methodContent + "}";
        /*------------------------------------------------编写java文件(结束)--------------------------------------------------*/

        /*--------------------------------------------将字符串写出到java文件中（开始）--------------------------------------------*/
        byte[] bytes = content.getBytes();
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            file = new File("Z:\\study\\code\\com\\study\\proxy\\dynamicProxy\\custom\\CustomerProxyObject.java");
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*--------------------------------------------将字符串写出到java文件中（结束）--------------------------------------------*/

        /*--------------------------------------------编译.java文件为class文件（开始）--------------------------------------------*/
        // 获取编译类
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 获取文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable units = fileManager.getJavaFileObjects(file);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, units);
        task.call();
        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*-----------------------------------------编译.java文件为class文件（结束）--------------------------------------------*/

        /*---------------------------------------加载.class文件并创建代理对象实例（开始）-----------------------------------------*/
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:Z:\\study\\code\\")});
            Class clazz = classLoader.loadClass("com.study.proxy.dynamicProxy.custom." + "CustomerProxyObject");
            // clazz.newInstance(); 无法通过此方法获取代理对象，因为代理对象中没有默认构造器
            // 获取构造方法，通过参数可以确定所要构造方法
            Constructor constructor = clazz.getConstructor(target.getClass().getInterfaces()[0]);
            proxyObject = constructor.newInstance(target);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        /*---------------------------------------加载.class文件并创建代理对象实例（结束）-----------------------------------------*/
        return proxyObject;
    }

}
