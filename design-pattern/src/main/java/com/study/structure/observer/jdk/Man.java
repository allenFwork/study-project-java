package com.study.structure.observer.jdk;

import java.util.Observable;
import java.util.Observer;

public class Man implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("---------------- Man update()... ------------------");
    }

}
