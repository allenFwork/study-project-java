package com.study.code_generator;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SqlServerGenerator {

    @Test
    public void generate() {
        try{
            List<String> warnings = new ArrayList<String>();
            boolean overWrite = true;
            String path = this.getClass().getResource("/").getPath();
            File configFile = new File(path + "generatorConfig.xml");
            System.out.println(configFile.exists());
            ConfigurationParser configurationParser = new ConfigurationParser(warnings);
            Configuration configuration = configurationParser.parseConfiguration(configFile);
            DefaultShellCallback defaultShellCallback = new DefaultShellCallback(overWrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("----------------- 生成完成 ... ---------------------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----------------- 生成失败 ... ---------------------");
        }
    }

}
