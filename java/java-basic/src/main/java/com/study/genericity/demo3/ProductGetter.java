package com.study.genericity.demo3;

import java.util.ArrayList;
import java.util.Random;

/**
 * 泛型的实际应用
 * 实例：模拟抽奖器
 */
public class ProductGetter<T> {

    static Random random = new Random();

    // 奖品
    private T product;

    // 奖品池
    ArrayList<T> list = new ArrayList<>();

    /**
     * 添加奖品
     * @param t 奖品
     */
    public void addProduct(T t) {
        list.add(t);
    }

    /**
     * 抽奖
     * @return
     */
    public T getProduct() {
        product = list.get(random.nextInt(list.size()));
        return product;
    }

    /**
     * 定义泛型方法
     * @param list 参数
     * @param <T> 泛型标识，具体类型，由调用方法的时候来指定。
     *            1.它也是泛型标识的列表，后面的参数中如果使用了泛型标识，那么该标识必须在表示列表中。例如下面这样：
     *                  <T, E> T getProduct(ArrayList<E> list)
     *            2.此处的泛型 T 与 “ProductGetter<T>” 中的 T 没有任何关系，该方法的泛型T只有调用时确定
     *
     * @return
     */
    public <T> T getProduct(ArrayList<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 静态的泛型方法，采用多个泛型类型
     * @param t
     * @param e
     * @param k
     * @param <T>
     * @param <E>
     * @param <K>
     */
    public static <T,E,K> void printType(T t, E e, K k) {
        System.out.println(t + "\t" + t.getClass().getSimpleName());
        System.out.println(e + "\t" + e.getClass().getSimpleName());
        System.out.println(k + "\t" + k.getClass().getSimpleName());
    }

    /**
     * 泛型可变参数的定义 (该方法是泛型方法, 可以是静态方法，也可以是非静态的)
     * @param e
     * @param <E>
     */
    public static <E> void print(E... e){
        for (int i = 0; i < e.length; i++) {
            System.out.println(e[i]);
        }
    }

}
