package com.study.proxy.dynamicProxy.custom;

import com.study.proxy.dynamicProxy.custom.invocationHandler.CustomerInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Description 自定义动态生成代理类： 通过模拟jdk的动态代理方式
 * @Version 2.0
 * @Author shiwei
 */
public class ProxyUtil2 {

    public static Object newInstance(Class targetClass, CustomerInvocationHandler customerInvocationHandler) {

        /*---------------------------------------------编写java文件(开始)-----------------------------------------------*/
        // 最终获取的代理对象
        Object proxyObject = null;
        // 换行
        String line = "\n";
        // 空格
        String tab = "\t";
        // 接口名字
        String interfaceName = targetClass.getSimpleName();
        // 接口的全限定名
        String interfaceNameAll = targetClass.getName();

        // 生成 .java文件 中的字符串内容
        String content;
        String packageContent = "package com.study.proxy.dynamicProxy.custom;" + line + line;
        String importContent  = "import com.study.proxy.dynamicProxy.custom.invocationHandler.CustomerInvocationHandler;" + line +
                                "import " + interfaceNameAll + ";" + line +
                                "import java.lang.reflect.Method;" + line + line;
        String classContent   = "public class CustomerProxyObject implements " + interfaceName + "{" + line + line;
        // 成员变量
        String fieldContent   = tab + "private CustomerInvocationHandler customerInvocationHandler;" + line + line;
        // 构造方法
        String constructContent = tab + "public CustomerProxyObject(CustomerInvocationHandler customerInvocationHandler) {" + line +
                                  tab + tab + "this.customerInvocationHandler = customerInvocationHandler;" + line +
                                  tab + "}" + line + line;

        // 代理类中重写的方法
        String methodContent = "";
        // 代理类中调用目标类中的方法
        String targetMethodContent = "";
        // 获取目标接口中的所有方法
        Method[] mehods = targetClass.getDeclaredMethods();
        for (Method method : mehods) {
            Class returnType = method.getReturnType();
            String returnTypeContent = returnType.getSimpleName();
            String methodName = method.getName();

            // 获取方法的参数类型组成的数组,例如方法为 f(String a,String b)，则args[]为 String.class String.class
            Class[] args = method.getParameterTypes();
            // 代理类中重写方法的参数
            String parametersContent = "";
            // 参数类型的数组
            String argClassArray = "new Class[]{";
            //
            String argsContent = "";
            for (int i = 0; i < args.length; i++) {
//                String temp = args[i].getClass().toString();
//                String temp2 = args[i].getName();
                argClassArray += args[i].getName() + ".class,";
            }
            for (int i = 0; i < args.length; i++) {
                parametersContent += args[i].getSimpleName()  + " p" + i + ",";
                argsContent += "p" + i + ",";
            }
            if (args != null && args.length != 0) {
                parametersContent = parametersContent.substring(0, parametersContent.length()-1);
                argsContent = argsContent.substring(0, argsContent.length()-1);
            }
            if (argClassArray.length() > 12){
                argClassArray = argClassArray.substring(0, argClassArray.length()-1);
            }
            methodContent += tab + "public " + returnTypeContent + " " + methodName + "(" + parametersContent + ") {" + line;
            // 判断调用目标类的方法是否有返回值
            if( !"void".equals(returnTypeContent)) {
                methodContent +=  tab + tab + "Method method = null;" + line +
                                  tab + tab + "try { " + line +
                                  tab + tab + tab + "method = "+interfaceName+".class.getDeclaredMethod(\"" + methodName + "\", " + argClassArray +"});" + line +
                                  tab + tab + "} catch (NoSuchMethodException e) {" + line +
                                  tab + tab + tab + "e.printStackTrace();" + line +
                                  tab + tab + "}" + line +
                                  tab + tab + "Object[] args = new Object[]{" + argsContent + "};" + line +
                                  tab + tab + "return ("+ returnTypeContent +") customerInvocationHandler.invoke(method, args);" + line +
                                  tab + "}" + line + line;
            } else {
                methodContent +=  tab + tab + "Method method = null;" + line +
                                  tab + tab + "try { " + line +
                                  tab + tab + tab + "method = "+interfaceName+".class.getDeclaredMethod(\"" + methodName + "\", " + argClassArray +"});" + line +
                                  tab + tab + "} catch (NoSuchMethodException e) {" + line +
                                  tab + tab + tab + "e.printStackTrace();" + line +
                                  tab + tab + "}" + line +
                                  tab + tab + "Object[] args = new Object[]{" + argsContent + "};" + line +
                                  tab + tab + "customerInvocationHandler.invoke(method, args);" + line +
                                  tab + "}" + line + line;
            }
        }
        content = packageContent + importContent + classContent + fieldContent + constructContent + methodContent + "}";
        System.out.println(content);
        /*------------------------------------------------编写java文件(结束)--------------------------------------------------*/

        /*--------------------------------------------将字符串写出到java文件中（开始）--------------------------------------------*/
        File file = null;
        try {
            file = new File("Z:\\study\\code\\com\\study\\proxy\\dynamicProxy\\custom\\CustomerProxyObject.java");
            if(!file.exists()){
                file.createNewFile();
            }
            // 字符流
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            Constructor constructor = clazz.getConstructor(CustomerInvocationHandler.class);
            proxyObject = constructor.newInstance(customerInvocationHandler);
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


