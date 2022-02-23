package com.study.util.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LogIpConfig extends ClassicConverter {

    private static String hostAddress;

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        if (null == hostAddress) {
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
                return hostAddress;
            } catch (UnknownHostException e) {

            }
            return "";
        }
        return hostAddress;
    }

}
