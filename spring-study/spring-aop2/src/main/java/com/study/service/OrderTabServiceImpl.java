package com.study.service;

import com.study.dao.OrderTabDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderTabServiceImpl implements ApplicationContextAware {

    private OrderTabDao orderTabDao;

    /**
     * spring容器如果管理多个同类型的对象，在一个类中通过自动装配的方式诸如这个类型的对象会报错
     * 现在有一个需求：根据运行时，调用被注入类型的那个类的方法，根据传入的参数的不同，自动转配不同的子类实现实例
     *
     * 方法一：
     *      通过在那个被注入的类中获取ApplicationContext类型对象，
     *      通过他获取spring管理bean对象，从而选择要注入的是哪一个bean对象
     *
     * 方法二：
     *     通过在该类中自动装配一个要注入类型的map，spring容器在实例化该类时，会将自动装配类型的所有bean对象放到map中，
     *     可以从map中选择要用到哪一个bean对象
     */

    /**
     * 方法二
     */
    @Autowired
    private Map<String,OrderTabDao> map;

    ApplicationContext applicationContext;

    public void query(String userName) {
        if("A".equals(userName)) {
            OrderTabDao orderTabDao = (OrderTabDao) applicationContext.getBean("orderTabDaoImpl");
            orderTabDao.update("A");
        } else if("B".equals(userName)) {
            OrderTabDao orderTabDao = (OrderTabDao) applicationContext.getBean("orderTabDaoImpl2");
            orderTabDao.update("B");
        }
    }

    /**
     * 方法一
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
