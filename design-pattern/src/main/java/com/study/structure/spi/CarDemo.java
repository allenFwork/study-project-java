package com.study.structure.spi;

import com.study.spi.api.CarInterface;

import java.util.Iterator;
import java.util.ServiceLoader;

public class CarDemo {

    public static void main(String[] args) {
        // jdk的spi通过ServiceLoader加载接口的实现类
        ServiceLoader<CarInterface> serviceLoader = ServiceLoader.load(CarInterface.class);
        Iterator<CarInterface> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            CarInterface carInterface = iterator.next();
            carInterface.getColor();
        }
    }

}
