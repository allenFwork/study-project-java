package com.study.util;

import com.study.exception.SpringException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public class BeanFactory {
//
//    private Map<String, Object> map;
//
//    public BeanFactory(String xml) {
//        parseXML(xml);
//    }
//
//    public void parseXML(String xml){
//
//        // 获取资源文件spring.xml的路径
//        String xmlPath = this.getClass().getResource("/").getPath() + xml;
//
//        File file = new File(xmlPath);
//        SAXReader reader = new SAXReader();
//        Document document = null;
//        try {
//            document = reader.read(file);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        // 获取 根标签<beans>
//        Element beansElement = document.getRootElement();
//
//    }
//}

/**
 * 模拟 spring ioc
 */
public class BeanFactory {

    /**
     * 存储bean对象的容器
     */
    Map<String, Object> map = new HashMap<String, Object>();

    /**
     * @Description 工厂
     * @param xml 用来告诉spring需要用来管理的对象信息的配置文件路径
     */
    public BeanFactory(String xml){
        parseXml(xml);
    }

    /**
     * @descrption 用来解析xml文件 （dom4j）
     * @param xml 配置文件路径
     */
    public void parseXml(String xml){
        // 获取当前项目所在路径
        String path = this.getClass().getResource("/").getPath() + xml;

        File file = new File(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);

            // 获取 根标签<beans>
            Element beansElement = document.getRootElement();

            /*------------------------- 解析是否开通了自动装配 --------------------------*/
            Attribute attribute = beansElement.attribute("default-autowired");
            boolean flag = false;
            if (attribute != null) {
                flag = true;
            }

//            // 所有的bean标签
//            List<Element> beanList = beansElement.elements();
//            // 一级循环
//            for(Iterator<Element> iterator = beanList.iterator(); iterator.hasNext(); ){
            for (Iterator<Element> iterator = beansElement.elementIterator(); iterator.hasNext(); ){
                /**
                 * setup 1 : 实例化对象
                 */
                // 获取beans标签里第一个bean标签，然后接着一直获取下一个bean标签
                Element beanElement = iterator.next();

                // 获取bean的id属性
                Attribute attributeId = beanElement.attribute("id");
                String beanName = attributeId.getValue();
                // 获取bean的class属性
                Attribute attributeClass = beanElement.attribute("class");
                String className = attributeClass.getValue();

                // 加载告知spring需要管理的类，也就是bean对应的类
                Class clazz = Class.forName(className);
                // spring容器管理的实例对象
                Object object = null;

                /*------------------------------循环bean的子标签开始：有明确写入的依赖------------------------------*/
                /**
                 * setup 2 : 维护依赖关系
                 *  看这个对象有没有依赖（判断是否有property属性标签，或者判断类是否有成员变量，这个成员变量是否通过spring注入管理）
                 *  如果有property属性标签，则表示要通过set注入
                 */
                // 二级循环： iterator2 表示bean的子标签，可以是property标签，也可以是constructor-arg标签
                for(Iterator<Element> iterator2 = beanElement.elementIterator(); iterator2.hasNext(); ) {
                    /**
                     * <property name="userDao" ref="userDao"></property>
                     * 获取ref的value，通过 value（也就是bean的id）得到对象（map）
                     * 获取name的value，然后根据value值获取一个Field的对象，再通过field的set方法set那个对象
                     */
                    // property标签 或者 constructor-arg标签
                    Element secondElement = iterator2.next();

                    // 判断是不是property标签,则是通过set方法注入的
                    if(secondElement.getName().equals("property")) {
                        // 由于是setter方法注入，所以此对象是有 无参构造方法的，没有特殊的构造方法
                        object = clazz.newInstance();
                        Object injectObject = map.get(secondElement.attribute("ref").getValue());
                        String nameValue = secondElement.attribute("name").getValue();
                        // 获取成员变量
                        Field field = clazz.getDeclaredField(nameValue);
                        // 因为对象的属性是private，私有的，所以不能直接通过set方法设置，得先设置权限
                        field.setAccessible(true);
                        field.set(object, injectObject);
                    // 判断是不是constructor-arg标签,则是通过构造方法注入的
                    } else {

                        // 这里需要补充，构造方法可能是多个参数 。。。
                        /*-------------------------- 循环constructor-arg的标签开始 --------------------------*/
//                        Iterator<Element> iterator3 = secondElement.elementIterator();
//                        while(iterator3.hasNext()){
//
//                        }

                        Object injectObject = map.get(secondElement.attribute("ref").getValue());
                        /**
                         * 这里这个注入的成员变量类型，应该通过constructor-arg标签的name的值找到类中的成员变量，
                         * 从而获取这个成员变量的类型，然后获取的构造方法的参数应该就是这个类型，从而再获得构造方法，
                         * 最后通过constructor-arg标签的ref的值找到spring容器（map）的实例，将其作为构造方法的参数传进去创建实例
                         *
                         * 这里需要补充 。。。
                         */
                        Class injectObjectClass = injectObject.getClass();
                        Constructor constructor = clazz.getConstructor(injectObjectClass.getInterfaces()[0]);
                        object = constructor.newInstance(injectObject);

                    }
                }
                /*----------------------------------循环bean的子标签结束：有明确写入的依赖---------------------------------*/


                /**
                 * 上面的子标签循环中，只要有依赖的，都已经完成注入了，这个优先级也确实是高于自动装配的
                 * 现在开始进行自动装配，类中有成员变量，但是这个成员没有通过上面注入，还是null的可以通过自动装配完成注入
                 */
                /*---------------------------------------自动装配开始----------------------------------------*/
                if(flag) {
                    if(attribute.getValue().equals("byType")) { // 按照 byType 方式装配
                        // 判断类中是否有成员变量，也就是判断类中是否需要注入spring容器管理的对象
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field: fields) {
                            // 得到属性的类型，比如String a，那么这里的field.getType()就是String.class
                            Class injectObjectClass = field.getType();
                            Object injectObject = null;
                            /**
                             * 由于是 byTYpe，所以需要遍历 map 当中的所有对象，
                             * 判断对象的类型是不是和和这个injectObjectClass相同
                             */
                            int count = 0;
                            for (String key: map.keySet()) {
                                Class temp = map.get(key).getClass().getInterfaces()[0];
                                if(temp.getName().equals(injectObjectClass.getName())) {
                                    injectObject = map.get(key);
                                    // 记录找到一个，因为可能找到多个
                                    count++;
                                }
                            }
                            if(count > 1) {
                                throw new SpringException("需要一个对象，但是找到了多个对象");
                            } else {
                                object = clazz.newInstance();
                                field.setAccessible(true);
                                field.set(object, injectObject);;
                            }
                        }

                    }
                }
                /*---------------------------------------自动装配结束----------------------------------------*/

                if(object == null) { // 没有子标签
                    object = clazz.newInstance();
                }
                map.put(beanName, object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 用来获取管理的对象，spring中的bean对象
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        return map.get(beanName);
    }

}
