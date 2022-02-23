package com.study.util.date;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 时间工具类
 **/
public class LocalDateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 格式化时间类型
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return formatDateByPattern(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化时间类型 精确到天
     *
     * @param date
     * @return
     */
    public static String formatDateDay(Date date) {
        if (date == null) {
            return "";
        }
        return formatDateByPattern(date, YYYY_MM_DD);
    }

    /**
     * 格式化时间类型
     *
     * @param date
     * @return
     */
    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化时间类型
     *
     * @param date
     * @return
     */
    public static String formatDate(LocalDateTime date, String format) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * date>localDateTime-
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    /**
     * 转化时间类型为date
     *
     * @param dateStr
     * @return
     */
    public static LocalDate parseDateString(String dateStr, String format) {
        if ("".equals(dateStr) || null == dateStr) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, df);
    }

    /**
     * 格式化时间类型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateByPattern(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转化时间类型为date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseStringByPattern(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两个日期相差天数，可跨年
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDifferenct(Date d1, Date d2) {
        return (int) ((d1.getTime() - d2.getTime()) / 86400000);
    }

    /**
     * 两个日期的天数，不考虑时间先后
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Integer getDiffDaysNoSort(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null;
        }

        if (d1.after(d2)) {
            return getDifferenct(d1, d2);
        } else {
            return getDifferenct(d2, d1);
        }
    }

    /**
     * 获取一个月的第一天作为字符串返回
     *
     * @return
     */
    public static String getMonthFirstDay() {
        LocalDate today = LocalDate.now();
        return getMonthFirstDay(today);
    }

    /**
     * 获取一个月的第一天
     *
     * @param localDate
     * @return
     */
    public static String getMonthFirstDay(LocalDate localDate) {
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return firstDay.format(DateTimeFormatter.ofPattern(YYYY_MM_DD));
    }

    /**
     * 获取上个月的最后一个 周 1
     *
     * @param currentDay
     * @param dayOfWeek
     * @return
     */
    public static LocalDate getBeforeMonthLastdayOfWeek(LocalDate currentDay, DayOfWeek dayOfWeek) {
        LocalDate beforMonthLastDay = currentDay.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate beforMonthLastFriday = beforMonthLastDay.with(dayOfWeek);
        //判断周五是下一个月的情况
        if (beforMonthLastFriday.getMonthValue() > beforMonthLastDay.getMonthValue()) {
            beforMonthLastFriday = beforMonthLastFriday.minusWeeks(1);
        }
        return beforMonthLastFriday;
    }


    /**
     * 获取未来第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(today);
        return result;
    }

    /**
     * 获取最近的周一时间
     *
     * @param date
     * @return
     */
    public static String getLastMonday(String date) {
        /**获取最近一个周一的数据*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 2);
        String lastMonday = getPastDate(-5, calendar.getTime());

        return lastMonday;

    }

    /**
     * 输入一个日期获取该日期未来几周的时间序列
     *
     * @param startDate
     * @param weekSize
     * @return
     */
    public static List<String> getDateLineByWeek(String startDate, int weekSize) {
        List<String> dateLine = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(startDate);
            for (int i = 0; i < weekSize; i++) {
                if (i == 0) {
                    dateLine.add(startDate);
                } else {
                    dateLine.add(getPastDate(7 * i, date));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLine;
    }

    /**
     * 将时间序列转为周数据时间序列
     *
     * @param dateLine
     * @return
     */
    public static List<String> getWeekDateLine(List<String> dateLine) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<String> weekDateLine = new LinkedList<>();

            /**获取起始日期最近的周一*/
            String startDate = getLastMonday(dateLine.get(0));

            /**获取结束日期最近的周一*/
            String endDate = getLastMonday(dateLine.get(dateLine.size() - 1));

            weekDateLine.add(startDate);

            while (sdf.parse(weekDateLine.get(weekDateLine.size() - 1)).getTime() < sdf.parse(endDate).getTime()) {

                String date = getPastDate(7, sdf.parse(weekDateLine.get(weekDateLine.size() - 1)));

                weekDateLine.add(date);
            }

            return weekDateLine;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 输入一个日期，获取该日期上个月的年月日
     *
     * @return
     */
    public static String getLastMonth(String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if ("".equals(currentDate) || null == currentDate) {
                date = new Date();
            } else {
                date = sdf.parse(currentDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date lastMonthDate = c.getTime();
        return sdf.format(lastMonthDate);
    }

    public static long now() {
        return System.currentTimeMillis();
    }

}

