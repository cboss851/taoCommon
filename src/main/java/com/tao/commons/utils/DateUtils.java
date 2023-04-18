package com.tao.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {
    /**
     * 仅显示年月日，例如 2025-08-11.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 显示年月日时分秒，例如 2025-08-11 09:51:53.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 仅显示时分秒，例如 09:51:53.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 仅显示年月，例如 2025-08
     */
    public static final String MONTH_TIME_PATTERN = "yyyy-MM";

    /**
     * 仅显示年，例如 2025
     */
    public static final String YEAR_TIME_PATTERN = "yyyy";

    /**
     * 每天的毫秒数 8640000.
     */
    public static final long MILLISECONDS_PER_DAY = 86400000L;

    /**
     * 每周的天数.
     */
    public static final long DAYS_PER_WEEK = 7L;

    /**
     * 每小时毫秒数.
     */
    public static final long MILLISECONDS_PER_HOUR = 3600000L;

    /**
     * 每分钟秒数.
     */
    public static final long SECONDS_PER_MINUTE = 60L;

    /**
     * 每小时秒数.
     */
    public static final long SECONDS_PER_HOUR = 3600L;

    /**
     * 每天秒数.
     */
    public static final long SECONDS_PER_DAY = 86400L;

    /**
     * 每个月秒数，默认每月30天.
     */
    public static final long SECONDS_PER_MONTH = 2592000L;

    /**
     * 每年秒数，默认每年365天.
     */
    public static final long SECONDS_PER_YEAR = 31536000L;

    /**
     * 常用的时间格式.
     */
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    /**
     * 得到当前日期字符串.
     *
     * @return String 日期字符串，例如2025-08-11
     */
    public static String getDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * 得到当前时间字符串.
     *
     * @return String 时间字符串，例如 09:51:53
     */
    public static String getTime(Date date) {
        return new SimpleDateFormat(TIME_FORMAT).format(date);
    }

    /**
     * 得到当前日期和时间字符串.
     *
     * @return String 日期和时间字符串，例如 2025-08-11 09:51:53
     */
    public static String getDateTime(Date date) {
        return new SimpleDateFormat(DATETIME_FORMAT).format(date);
    }

    /**
     * 获取指定日期的字符串格式.
     *
     * @param date    需要格式化的时间，不能为空
     * @param pattern 时间格式，例如"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @return String 格式转换之后的时间字符串.
     */
    public static String getDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取当前年份字符串.
     *
     * @return String 当前年份字符串，例如 2025
     */
    public static String getYear(Date date) {
        return new SimpleDateFormat("yyyy").format(date);
    }

    /**
     * 获取当前月份字符串.
     *
     * @return String 当前月份字符串，例如 08
     */
    public static String getMonth(Date date) {
        return new SimpleDateFormat("MM").format(date);
    }

    /**
     * 获取当前天数字符串.
     *
     * @return String 当前天数字符串，例如 11
     */
    public static String getDay(Date date) {
        return new SimpleDateFormat("dd").format(date);
    }

    /**
     * 获取当前星期字符串.
     *
     * @return String 当前星期字符串，例如星期二
     */
    public static String getWeek(Date date) {
        return new SimpleDateFormat("E").format(date);
    }


    /**
     * 加减分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, Integer minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 加减小时
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, Integer hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 加减天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 加减月
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, Integer months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 加减年
     *
     * @param date
     * @param years
     * @return
     */
    public static Date addYears(Date date, Integer years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 天开始时间 2025-08-11 00:00:00
     *
     * @param date
     * @return
     */
    public static String getStartDay(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date) + " 00:00:00";
    }

    /**
     * 天结束时间 2025-08-11 23:59:59
     *
     * @param date
     * @return
     */
    public static String getEndDay(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date) + " 23:59:59";
    }


    /**
     * 月开始时间 2025-08-01
     *
     * @param date
     * @return
     */
    public static String getStartMonth(Date date) {
        return new SimpleDateFormat(MONTH_TIME_PATTERN).format(date) + "-01";
    }

    /**
     * 月结束日期 2025-04-30
     *
     * @param date
     * @return
     */
    public static String getEndMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
    }

    /**
     * 周一日期 2025-08-01
     *
     * @param date
     * @return
     */
    public static String getStartWeek(Date date) {
        // 获取当前时间所在的周的中国式星期一
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            // 如果当前日期是星期日，向前推6天
            calendar.add(Calendar.DAY_OF_MONTH, -6);
        } else {
            // 向前推 dayOfWeek - 2 天，即可得到本周的星期一
            calendar.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - 2));
        }
        return new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
    }

    /**
     * 周日日期 2025-04-30
     *
     * @param date
     * @return
     */
    public static String getEndWeek(Date date) {
        // 获取当前时间所在的周的中国式星期日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SUNDAY) {
            // 如果当前日期不是星期日，向后推 7 - dayOfWeek 天，即可得到本周的星期日
            calendar.add(Calendar.DAY_OF_MONTH, 7 - dayOfWeek);
        }
        return new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
    }

}