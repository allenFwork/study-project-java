package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 最重要的demo
 * 从一个文件中读取数据，写入到另一个文件
 */
public class Dome4 {
    public static void main(String[] args) throws Exception {

        // java.io默认定位到当前用户目录(“user.dir”)下，即：工程根目
        System.out.println(System.getProperty("user.dir")); // C:\Users\86131\IdeaProjects\study-project-java
//        FileInputStream fileInputStream = new FileInputStream("dome4read.txt");
//        FileOutputStream fileOutputStream = new FileOutputStream("dome4write.txt");
        FileInputStream fileInputStream = new FileInputStream("netty-study/nio/src/test/resources/dome4read.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("netty-study/nio/src/test/resources/dome4write.txt");

        FileChannel channelRead = fileInputStream.getChannel();
        FileChannel channelWrite = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        while (true) {
            // byteBuffer的position变为0，limit变为100（capacity值），
            // 底层HeapByteBuffer对象的hb属性中的字节数组不发生任何变化（保存着之前写入的数据）
            byteBuffer.clear(); // 不加上这行代码，会死循环
            System.out.println(byteBuffer.position());

            /*
             * channelRead.read(byteBuffer)：
             *   1.如果 byteBuffer 中 position 和 limit 的值相等，钟祥同一个位置，
             *     那么就无法从channelRead中读取数据并放到 byteBuffer中，此时无论channelRead中能否读到数据都返回 0 ;
             *   2.如果 byteBuffer 中 position 和 limit 的值不相等，即 position < limit时，
             *     从 channelRead 中读取数据并将数据写入到 byteBuffer中，此时写入了多少个字节，那么就返回多少 ;
             *   3.如果 byteBuffer 中 position 和 limit 的值不相等，即 position < limit时，
             *     从 channelRead 中读取不到任何数据，那么此时返回 -1 。
             */
            int readNumber = channelRead.read(byteBuffer);

            System.out.println(readNumber);
            if (-1 == readNumber) {
                break;
            }
            byteBuffer.flip();
            channelWrite.write(byteBuffer);
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}
