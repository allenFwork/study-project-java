package com.study.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public final static String DATE_PATTERN1 = "yyyy-MM-dd";
    public final static String DATE_PATTERN2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param date
     * @return
     * @descrption 将Date类型转化为 yyyy-MM-dd HH:mm:ss 类型的字符串
     */
    public static String toDateString(Date date, String datePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String dateTime = "";
        try {
            dateTime = simpleDateFormat.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dateTime;
    }

    /**
     * @param dateString
     * @return
     * @description 将String类型转化为date类型
     */
    public static Date StringToDate(String dateString, String datePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static long dateToLong(Date date) {
        return date.getTime();
    }


}
