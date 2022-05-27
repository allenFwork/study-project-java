package com.study.ineternet;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InternetDemo {
    public static void main(String[] args) throws UnknownHostException {
//        Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
        InetAddress address = InetAddress.getLocalHost();
        // 获取计算机名称和ip地址
        System.out.println(address);
        String hostAddress = address.getHostAddress();
        // 获取ip地址
        System.out.println(hostAddress);
        String hostName = address.getHostName();
        // 获计算机名称
        System.out.println(hostName);
    }
}
