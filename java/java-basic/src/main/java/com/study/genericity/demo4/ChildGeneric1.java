package com.study.genericity.demo4;

/**
 * 子类泛型类（第一种情况）
 * 1.子类也是泛型类，子类和父类的泛型类型要一致: ChildGeneric1<T> extends ParentGeneric<T>
 * 2.子类可以有多个泛型，但是必须有一个与父类额泛型一致：ChildGeneric1<T,K,L> extends ParentGeneric<T>
 *
 * @param <T>
 */
public class ChildGeneric1<T> extends ParentGeneric<T> {

    @Override
    public T getValue() {
        return super.getValue();
    }

}
