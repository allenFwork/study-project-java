package com.study.common_api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RuntimeDemo {

    public static void basicMethod() {

        // 1.获取Runtime对象：public static Runtime getRuntime() 当前系统的运行环境对象
        Runtime runtime = Runtime.getRuntime();

        // 2.停止虚拟机：public void exit(int status) 停止虚拟机
        // runtime.exit(0);
        // System.out.println("测试exit方法执行后, 是否向下执行 ...");

        // 3.获得CPU的线程数：public int availableProcessors()
        int processorCount = runtime.availableProcessors();
        System.out.println("CPU的线程数：" + processorCount); //CPU的线程数：8

        /**
         * 虚拟机配置：
         * -Xms128m JVM初始分配的堆内存 (已配置)
         * -Xmx512m JVM最大允许分配的堆内存，按需分配 （已配置）
         * -XX:PermSize=64M JVM初始分配的非堆内存 （未配置，使用默认的）
         * -XX:MaxPermSize=128M JVM最大允许分配的非堆内存，按需分配（未配置，使用默认的）
         */
        // 4.JVM能从系统中获取总内存大小,单位byte字节：public long maxMemory()
        long maxMemory = runtime.maxMemory();
        System.out.println("能从系统中获取的总内存大小：" + maxMemory / 1024 / 1024 + "MB"); //455MB

        // 5.已经获取的总内存大小,单位byte字节
        long totalMemory = runtime.totalMemory();
        System.out.println("已经从系统中获取的总内存大小：" + totalMemory / 1024 / 1024 + "MB"); //123MB

        // 6.JVM剩余内存大小：public long freeMemory()
        long freeMemory = runtime.freeMemory();
        System.out.println("JVM剩余的内存大小：" + freeMemory / 1024 / 1024 + "MB"); //119MB


        // 7.运行cmd命令：public Process exec(string command)
        try {
            /**
             * shutdown :关机（加上参数才能执行）
             *      -s: 默认在1分钟之后关机
             *      -s -t 指定时间: 指定关机时间
             *      -a: 取消关机操作
             *      -r: 关机并重启
             */
            System.out.println("执行cmd命令，准备关机 ... ");
            runtime.exec("shutdown -s -t 3600");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 特殊需求实现一个小游戏
    public static void specialFunction() {
        new MyJFrame();
    }

    public static void main(String[] args) {
        // basicMethod();
        specialFunction();
    }
}

class MyJFrame extends JFrame implements ActionListener {

    JButton yesBut = new JButton("帅爆了");
    JButton midBut = new JButton("一般般吧");
    JButton noBut = new JButton("不帅，有点磕碜");
    JButton dadBut = new JButton("饶了我吧！");

    //决定了上方的按钮是否展示
    boolean flag = false;

    public MyJFrame() {
        initJFrame();
        initView();
        //显示
        this.setVisible(true);
    }

    private void initView() {
        this.getContentPane().removeAll();

        if (flag) {
            //展示按钮
            dadBut.setBounds(50, 20, 100, 30);
            dadBut.addActionListener(this);
            this.getContentPane().add(dadBut);
        }

        JLabel text = new JLabel("你觉得自己帅吗？");
        text.setFont(new Font("微软雅黑", 0, 30));
        text.setBounds(120, 150, 300, 50);

        yesBut.setBounds(200, 250, 100, 30);
        midBut.setBounds(200, 325, 100, 30);
        noBut.setBounds(160, 400, 180, 30);

        yesBut.addActionListener(this);
        midBut.addActionListener(this);
        noBut.addActionListener(this);

        this.getContentPane().add(text);
        this.getContentPane().add(yesBut);
        this.getContentPane().add(midBut);
        this.getContentPane().add(noBut);

        this.getContentPane().repaint();
    }

    private void initJFrame() {
        //设置宽高
        this.setSize(500, 600);
        //设置标题
        this.setTitle("恶搞好基友");
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //置顶
        this.setAlwaysOnTop(true);
        //居中
        this.setLocationRelativeTo(null);
        //取消内部默认布局
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == yesBut) {
            //给好基友一个弹框
            showJDialog("xxx，你太自信了，给你一点小惩罚");
            try {
                Runtime.getRuntime().exec("shutdown -s -t 3600");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            flag = true;
            initView();

        } else if (obj == midBut) {
            System.out.println("你的好基友点击了一般般吧");

            //给好基友一个弹框
            showJDialog("xxx，你还是太自信了，也要给你一点小惩罚");

            try {
                Runtime.getRuntime().exec("shutdown -s -t 7200");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            flag = true;
            initView();


        } else if (obj == noBut) {
            System.out.println("你的好基友点击了不帅");

            //给好基友一个弹框
            showJDialog("xxx，你还是有一点自知之明的，也要给你一点小惩罚");

            try {
                Runtime.getRuntime().exec("shutdown -s -t 1800");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            flag = true;
            initView();
        } else if (obj == dadBut) {
            //给好基友一个弹框
            showJDialog("xxx，这次就饶了你~");
            try {
                Runtime.getRuntime().exec("shutdown -a");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void showJDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);

        //创建Jlabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //让弹框展示出来
        jDialog.setVisible(true);
    }

}
