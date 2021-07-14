package com.study.io;

import java.io.*;

public class IOChangeType {

    /**
     * 获取文件的二级制数组
     * @param filePath
     * @return
     */
    public static byte[] FileToByteArray(String filePath) {
        // 文件对象
        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            // InputStream是抽象类,将文件对象转化为文件流
            inputStream = new FileInputStream(file);

            /*---------------------- 再将文件流转化为字节数组 ---------------------*/
            // 定义用来存放数据(1M)的缓存数组
            byte[] buffer = new byte[1024*1024];

            // 输出数组流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            /*----------------- 读取文件流，将文件流放到输出数组流中 -----------------*/
            // 设置每次传输的个数,若没有缓冲的数据大，则返回剩下的数据，没有数据返回-1
            int len = -1;

            // 将读取的二进制字节写入到缓存数组中,并将读取字节的个数转化为int型个数赋值给len
            while((len = inputStream.read(buffer)) != -1){
                // 每次读取len长度数据后,将缓存数组中的全部写入到输出字节数组流中
                byteArrayOutputStream.write(buffer,0, len);
            }

            //刷新管道数据
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源,文件流需要关闭,字节数组流无需关闭
            if( null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static File ByteArrayToFile(byte[] fileArray, File file) {

        // 输入流 - 管道
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileArray);

        // 缓存数组空间
        byte[] buffer = new byte[1024];

        // 读取个数
        int len = -1;

        // 文件输出流
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            while ((len = byteArrayInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
