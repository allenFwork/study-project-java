package com.study.structure.observer.customer3;

public class Woman implements Person {

    @Override
    public void perform(MovieEvent movieEvent) {
        if ("高潮场景".equals(movieEvent.getCircumstance())) {
            System.out.println("------------观察者（Woman）观看电影高潮无聊极了-----------");
        } else {
            System.out.println("------------观察者（Woman）观看电影悲伤悲伤极了-----------");
        }
    }

}
