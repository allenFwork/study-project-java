package com.study.util.poi;

import org.junit.Test;

public class PoiUtilTest {

    @Test
    public void writeDemoTest() {
        System.out.println("POI工具类测试：write方法");
        try {
            PoiDemo.write();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readDemoTest() {
        System.out.println("POI工具类测试：read方法");
        try {
            PoiDemo.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
