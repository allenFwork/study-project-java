package com.study.io;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {

    /**
     * 查看不同编码下中文字符所占的字节数
     * @param content
     */
    public static void toHexString(String content) {

        System.out.println("utf-8编码格式下:");
        // 将字符串转换为字节数组，根据项目的编码格式
        // utf-8编码中文占用3个字节，英文占用1个字节。
        byte[] bytes = content.getBytes();
        for(byte b : bytes) {
            //把字节(转换成了int)以16进制的方式显示
            System.out.println(Integer.toHexString(b & 0xff) + " ");
        }

        System.out.println("--------------------------------------");

        System.out.println("gbk编码格式下:");
        // gbk编码，中文占用2个字节，英文占用1个字节
        try {
            bytes = content.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(byte b : bytes) {
            System.out.println(Integer.toHexString(b  & 0xff) + " ");
        }

    }


    public static void encodeChange(String content) {

        byte[] bytes = null;
        try {
            bytes = content.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
