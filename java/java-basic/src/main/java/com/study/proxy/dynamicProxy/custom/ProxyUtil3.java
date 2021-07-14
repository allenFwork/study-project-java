package com.study.proxy.dynamicProxy.custom;//package com.luban.proxy;
//
//import com.luban.util.handler.CustomerInvocationHandler;
//
//import javax.tools.JavaCompiler;
//import javax.tools.StandardJavaFileManager;
//import javax.tools.ToolProvider;
//import java.io.File;
//import java.io.FileWriter;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLClassLoader;
//
//public class ProxyUtil2 {
//
//
//    /**
//     * 自己模拟的动态代理 V2.0
//     * @param targetInterface 目标对象实现的接口
//     * @param customerInvocationHandler 包含invoke方法
//     * @return
//     */
//    public static Object newInstance(Class targetInterface, CustomerInvocationHandler customerInvocationHandler) {
//
//        // 最终获取的代理对象
//        Object proxyObject = null;
//        // 换行
//        String line = "\n";
//        // 空格
//        String tab = "\t";
//
//        // 获取目标接口中的所有方法
//        Method[] methods = targetInterface.getDeclaredMethods();
//
//        // 获取目标接口的名称
//        String interfaceName = targetInterface.getSimpleName();
//        // 生成 .java文件 中的字符串内容
//        String content = "";
//        String packageContent = "package com.google;" + line;
//        String importContent = "import " + targetInterface.getName() + ";" + line +
//                "import com.luban.util.handler.CustomerInvocationHandler;" + line +
//                "import java.lang.reflect.Method;" + line ;
//        String classFirstLine = "public class $Proxy implements " + interfaceName + "{" + line;
//
//        String fieldContent = tab + "private CustomerInvocationHandler customerInvocationHandler;" + line;
//
//        /*----------------------------------------构造方法内容开始------------------------------------------*/
//        String construcortContent = tab + "public $Proxy(CustomerInvocationHandler customerInvocationHandler) {" + line +
//                tab + tab + "this.customerInvocationHandler = customerInvocationHandler;" + line +
//                tab + "}" + line;
//
//        /*--------------------------------------代理接口方法内容开始----------------------------------------*/
//        String methodContent = "";
//        for (Method method : methods){
//            String returnTypeName = method.getReturnType().getSimpleName();
//            String methodName = method.getName();
//            // 获取方法的参数类型组成的数组,例如方法为 f(String a,String b)，则args[]为 String.class String.class
//            Class args[] = method.getParameterTypes(); // 该方法返回的本身就是Class的对象，不是Object类型对象
//            String argsContent = "";
//            String parameterContent = "";
//            int argsCount = 0;
//            for(Class arg : args) {
//                String temp = arg.getSimpleName();
//                argsContent += temp + " parameter" + argsCount + ",";
//                parameterContent += "parameter" + argsCount + ",";
//                argsCount++;
//            }
//            // 如果有参数，就截取掉最后的逗号
//            if(argsContent.length() > 0 ){
//                argsContent = argsContent.substring(0, argsContent.lastIndexOf(",")-1);
//            }
//            if(parameterContent.length() > 0 ){
//                parameterContent = argsContent.substring(0, argsContent.lastIndexOf(",")-1);
//            }
//
//            methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") { " + line +
//
//                             /*-------------------------调用invoke方法实现添加的代理逻辑开始--------------------------*/
//                    // 1. 获取 method
////                             tab + tab + "Method method = " + interfaceName + ".class.getDeclaredMethod(\"" + methodName + "\");" + line ;
//                    tab + tab + "try {" + line +
//                    tab + tab + tab + "Method method = Class.forName(\"" + targetInterface.getName() + "\").getDeclaredMethod(\"" + methodName + "\");" + line ;
//            // 2. 获取参数
//            if(returnTypeName.equals("void")) {
//                methodContent += tab + tab + tab + "customerInvocationHandler.invoke(method, null);" + line +
//                        tab + tab + "}";
//            } else {
//                // 构建参数 Object[] args = {String.class, Integer.class};
//                // 使用强转连解决返回类型不一致问题
//                methodContent += tab + tab + tab + "return ("+returnTypeName+")customerInvocationHandler.invoke(method, new Object());" + line +
//                        tab+ tab + "}";
//            }
//            methodContent += "catch(Exception e){\n" + line +
//                    tab + tab + "}" + line + tab + "}" + line;
//
//        }
//        /*--------------------------------------代理接口方法内容结束----------------------------------------*/
//
//        content += packageContent + importContent + classFirstLine + fieldContent + construcortContent + methodContent + "}";
//        // 将content写入到.java文件中去，因为content是字符，所以使用字符流
//        File file = new File("d:\\com\\google\\$Proxy.java");
//
//        try {
//            if(!file.exists()){
//                file.createNewFile();
//            }
//            // 字符流
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(content);
//            fileWriter.flush();
//            fileWriter.close();
//
//            // 第二步：动态编译
//            // 获取编译类
//            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//            // 获取文件管理器
//            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
//            Iterable units = fileManager.getJavaFileObjects(file);
//
//            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, units);
//            task.call();
//            fileManager.close();
//
//            // ClassLoader加载类 .class
//            URL[] urls = new URL[]{new URL("file:d:\\\\")};
//            URLClassLoader urlClassLoader = new URLClassLoader(urls);
//            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");
//            // 获取构造方法，通过参数可以确定所要构造方法
//            Constructor constructor = clazz.getConstructor(CustomerInvocationHandler.class);
//            proxyObject = constructor.newInstance(customerInvocationHandler);
//            // clazz.newInstance(); 无法通过此方法获取代理对象，因为代理对象中没有默认构造器
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return proxyObject;
//    }
//
//}