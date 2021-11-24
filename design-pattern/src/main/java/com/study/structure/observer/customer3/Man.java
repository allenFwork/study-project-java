package com.study.structure.observer.customer3;

public class Man implements Person {

    @Override
    public void perform(MovieEvent movieEvent) {
        if ("高潮场景".equals(movieEvent.getCircumstance())) {
            System.out.println("------------观察者（Man）观看电影高潮激动极了-----------");
        } else {
            System.out.println("------------观察者（Man）观看电影悲伤无聊极了-----------");
        }
    }

}
