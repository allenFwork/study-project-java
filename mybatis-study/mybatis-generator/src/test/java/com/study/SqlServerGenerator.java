package com.study;

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
//            File configFile = new File(path + "/generatorConfig.xml");
            File configFile = new File("C:\\Users\\86131\\IdeaProjects\\rise-server-master\\rise-dao\\target\\test-classes\\generatorConfig.xml");
//            File configFile = new File("generatorConfig2.xml");
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
