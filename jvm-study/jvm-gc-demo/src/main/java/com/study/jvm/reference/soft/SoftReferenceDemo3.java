package com.study.jvm.reference.soft;


import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * 软引用案例3 - 引用队列使用
 * 启动时，设置最大堆内存：-Xmx200m
 */
public class SoftReferenceDemo3 {

    public static void main(String[] args) throws IOException {

        ArrayList<SoftReference> softReferences = new ArrayList<>();
        ReferenceQueue<byte[]> queues = new ReferenceQueue<byte[]>();
        /*
            因为堆内存最大设置为200M，即实际对中能存放数据的空间小于200M，所以每创建100M大小的数组bytes时，之前软引用包裹的数组会被回收掉，并将该引用放到queues队列中。
            因为循环了10次，所以最终queues中会有9个软引用对象，第十次循环创建的软引用包裹的空间不用回收，所以没有放到queues队列中。
         */
        for (int i = 0; i < 10; i++) {
            byte[] bytes = new byte[1024 * 1024 * 100];
            SoftReference studentRef = new SoftReference<byte[]>(bytes, queues);
            softReferences.add(studentRef);
        }

        SoftReference<byte[]> ref = null;
        int count = 0;
        while ((ref = (SoftReference<byte[]>) queues.poll()) != null) {
            // 此处只进行了计数，实际需要通过遍历队列，将软引用的强引用删除掉
            count++;
        }
        System.out.println(count);
    }
}
