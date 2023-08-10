package com.study.genericity.demo8;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {

        ArrayList<Animal> animals = new ArrayList<>();
        ArrayList<Cat> cats = new ArrayList<>();
        ArrayList<MiniCat> miniCats = new ArrayList<>();

        /**
         * ArrayList集合的addAll方法使用的就是泛型上限通配符， public boolean addAll(Collection<? extends E> c)
         * 所以cats集合调用addAll方法时，上限是Cat，只能传入 cats 和 miniCats
         */
        cats.addAll(cats);
        cats.addAll(miniCats);

        // showAnimal(animals);
        showAnimal(cats);
        showAnimal(miniCats);

        showAnimalDown(animals);
        showAnimalDown(cats);
        // showAnimalDown(miniCats);

    }

    /**
     * 泛型上限通配符，传递的集合类型，只能是Cat或Cat的子类类型。
     * @param list
     */
    public static void showAnimal(ArrayList<? extends Cat> list) {

        /**
         * 下面三个 lis.add方法编译报错，因为在此方法内部，使用add方法时，无法判断传入的具体是什么类型
         * 例如，调用时传入： ArrayList<MiniCat>这样的集合实例，那么向其中添加 list.add(new Cat()) 就会报错
         */
        // list.add(new Animal());
        // list.add(new Cat());
        // list.add(new MiniCat());
        for (int i = 0; i < list.size(); i++) {
            Cat cat = list.get(i);
            System.out.println(cat);
        }
    }

    /**
     * 类型通配符下限，要求集合只能是Cat或Cat的父类类型
     * @param list
     */
    public static void showAnimalDown(List<? super Cat> list) {
        // 可以在方法内部添加实例对象
//        list.add(new Cat());
//        list.add(new MiniCat());
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            System.out.println(o);
        }
    }

}
