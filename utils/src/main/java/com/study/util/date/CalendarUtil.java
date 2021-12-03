package com.study.util.date;

import java.util.Calendar;

public class CalendarUtil {

    /**
     * 获取上个月的年月日
     */
    public static String getLastMonthDateString(String type) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        // 月份从0开始
        Integer month = calendar.get(Calendar.MONTH) + 1;
        // 日期
        Integer day = calendar.get(calendar.DAY_OF_MONTH);
        // 12小时制
        Integer twelveHour = calendar.get(calendar.HOUR);
        // 24小时制
        Integer twentyFourHour = calendar.get(calendar.HOUR_OF_DAY);
        // 分钟
        Integer minute = calendar.get(calendar.MINUTE);
        // 秒钟
        Integer second = calendar.get(calendar.SECOND);

        // 换算为上个月
        if (month == 1) {
            year--;
            month = 12;
        } else {
            month--;
        }

        String monthStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        String dayStr;
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        String minuteStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        String secondStr;
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }

        String time = "";
        if ("24".equals(type)) {
            if (twentyFourHour < 10) {
                time = year + "-" + monthStr + "-" + dayStr + " " + "0" + twentyFourHour + ":" + minuteStr + ":" + secondStr;
            } else {
                time = year + "-" + monthStr + "-" + dayStr + " " + twentyFourHour + ":" + minuteStr + ":" + secondStr;
            }
        } else {
            if (twelveHour < 10) {
                time = year + "-" + monthStr + "-" + dayStr + " " + "0" + twelveHour + ":" + minuteStr + ":" + secondStr;
            } else {
                time = year + "-" + monthStr + "-" + dayStr + " " + twelveHour + ":" + minuteStr + ":" + secondStr;
            }

        }
        return time;
    }

    /**
     * 获取 count个月前的年月日
     */
    public static String getMonthDateString(String type, int count) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        // 月份从0开始
        Integer month = calendar.get(Calendar.MONTH) + 1;
        // 日期
        Integer day = calendar.get(calendar.DAY_OF_MONTH);
        // 12小时制
        Integer twelveHour = calendar.get(calendar.HOUR);
        // 24小时制
        Integer twentyFourHour = calendar.get(calendar.HOUR_OF_DAY);
        // 分钟
        Integer minute = calendar.get(calendar.MINUTE);
        // 秒钟
        Integer second = calendar.get(calendar.SECOND);

        // 换算为count个月前
        if (month <= count) {
            year--;
            month = 12 - (count - month);
        } else {
            month = month - count;
        }

        String monthStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        String dayStr;
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        String minuteStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }
        String secondStr;
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }

        String time = "";
        if ("24".equals(type)) {
            if (twentyFourHour < 10) {
                time = year + "-" + monthStr + "-" + dayStr + " " + "0" + twentyFourHour + ":" + minuteStr + ":" + secondStr;
            } else {
                time = year + "-" + monthStr + "-" + dayStr + " " + twentyFourHour + ":" + minuteStr + ":" + secondStr;
            }
        } else {
            if (twelveHour < 10) {
                time = year + "-" + monthStr + "-" + dayStr + " " + "0" + twelveHour + ":" + minuteStr + ":" + secondStr;
            } else {
                time = year + "-" + monthStr + "-" + dayStr + " " + twelveHour + ":" + minuteStr + ":" + secondStr;
            }

        }
        return time;
    }

}
