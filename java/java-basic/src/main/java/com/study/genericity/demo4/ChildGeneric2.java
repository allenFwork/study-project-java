package com.study.genericity.demo4;

/**
 * 泛型类派生子类，子类不是泛型类（第二种情况）
 * 那么父类要明确数据类型: ChildGeneric2 extends ParentGeneric<Integer>
 */
public class ChildGeneric2 extends ParentGeneric<Integer> {

    @Override
    public Integer getValue() {
        return super.getValue();
    }

}
