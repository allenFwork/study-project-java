package com.study.springboot.test.demo.service.impl;

import com.study.springboot.test.demo.service.JavaAgentTestService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class JavaAgentTestServiceImpl implements JavaAgentTestService {

    @Override
    public void testReadLocalExcel() {
        // 方法一：
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/excel_template/test_model.xlsx");
        if (resourceAsStream != null) {
            System.out.println("读取本地模板文件成功...");
            try {
                int length = resourceAsStream.available();
                System.out.println("本地文件大小：" + length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 方法二：
        resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("excel_template/test_model.xlsx");
        if (resourceAsStream != null) {
            System.out.println("读取本地模板文件成功...");
            try {
                int length = resourceAsStream.available();
                System.out.println("本地文件大小：" + length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String classPath = System.getProperty("java.class.path");
        System.out.println("classPath的路径有：" + classPath);

    }

}
