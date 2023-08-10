package com.study.genericity.demo5;

/**
 * 实现泛型接口的类不是泛型类：
 *  1.如果泛型接口没有设置泛型，那么默认是Object
 *  2.如果泛型接口设置泛型，那么该泛型必须是具体的数据类型，例如下面的代码：
 *    class ChildGeneric1 implements ParentGeneric<T> 写法错误
 */
public class ChildGeneric1 implements ParentGeneric<String> {

    @Override
    public String getKey() {
        return "非泛型类的是实现方法测试。。。";
    }

}
