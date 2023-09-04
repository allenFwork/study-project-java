package com.study.util.excel.big_data;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.apache.commons.compress.utils.Sets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class CsvUtil {

    /**
     * 多线程读取CSV文件：获取对应列的s数据去重放入集合集合
     * 使用了 cn.hutool 工具依赖包
     *
     * @param filePath
     */
    public static Collection<String> duplicateRemoval(String filePath, int[] columnArray) {

        // 记录效率：测试时间开始
        TimeInterval timer = DateUtil.timer();

        // 并行流 没有数据安全, 需要synchronized
        Collection<String> strSet = Collections.synchronizedCollection(Sets.newHashSet());
        File touch = FileUtil.touch(filePath);
        FileInputStream fileInputStream = IoUtil.toStream(touch);
//        BufferedReader utf8Reader = IoUtil.getUtf8Reader(fileInputStream);
        BufferedReader utf8Reader = IoUtil.getReader(fileInputStream, "UTF-8");
        // 读取每一行获取每一行的字符串，形成集合，在处理为对应的流，csv文件中每一行的数据都是 “ xxx,xxx,xxx,xxx ” 这样的格式
        Stream<String> lines = utf8Reader.lines();
        // 流进行并行处理
        lines.parallel()
                .forEach(s -> {
                            String[] temp = s.split(",");
                            String result = "";
                            for (int i : columnArray) {
                                result += temp[i];
                            }
                            strSet.add(result);
                        }
                );
        // 打印有多少条数据（去重后的的数据）
        System.out.println("----- 去重后的数据量为 -----" + strSet.size());
        // 处理时间
        System.out.println("----- 执行结束 -----" + timer.interval() + "ms");
        System.out.println("----- 执行结束 -----" + Double.valueOf(timer.interval()) / 1000 + "s");
        return strSet;
    }




}

