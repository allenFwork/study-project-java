package com.study.genericity.demo5;

/**
 * 实现泛型接口的实现类是一个泛型类：
 * 1.泛型类的泛型标识中与泛型接口的泛型标识一致: class ChildGeneric2<T> implements ParentGeneric<T>
 * 2.泛型类的泛型标识中必须有一个与泛型接口的泛型标识一致: class ChildGeneric2<E,T> implements ParentGeneric<T>
 */
public class ChildGeneric2<E, T> implements ParentGeneric<E> {

    private E key;
    private T value;

    public ChildGeneric2(E key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    public T getValue() {
        return value;
    }

}
