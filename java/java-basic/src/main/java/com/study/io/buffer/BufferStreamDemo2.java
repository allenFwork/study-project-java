package com.study.io.buffer;

import java.io.*;

public class BufferStreamDemo2 {

    public static void specialMethod() {
        // 会自动释放资源
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\documents\\study\\test\\1111.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\documents\\study\\test\\1111_2.txt"))
        ) {
            String line = null;
            while ((line = br.readLine()) != null) { // 此处是while，不是if
                System.out.println(line);
                bw.write(line);
                // 写出换行, 如果不执行newLine方法，那么会将数据全部卸载一行里面，除非字符串中有 "\r" 换行符
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        specialMethod();
    }

}
