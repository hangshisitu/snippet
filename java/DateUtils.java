package com.xiaoying.credit.asset.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 * @author qiaojun.xiao
 */
public class DateUtils {

    private static DateTimeFormatter str6 = DateTimeFormatter.ofPattern("yyyyMM");
    private static DateTimeFormatter str8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static DateTimeFormatter str14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static DateTimeFormatter str7 = DateTimeFormatter.ofPattern("yyyy-MM");
    private static DateTimeFormatter str10 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter str19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter strTime6 = DateTimeFormatter.ofPattern("HHmmss");
    private static DateTimeFormatter strTime8 = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * @param timeStamp 时间戳
     * @return
     */
    public static LocalDateTime timeStamp2LocalDataTime(long timeStamp)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(timeStamp);
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @param dateTime
     * @return 时间戳
     */
    public static long localDataTime2TimeStamp(LocalDateTime dateTime)
    {
        return dateTime.toEpochSecond( OffsetDateTime.now().getOffset());
    }

    /**
     * @param date 日期
     * @return
     */
    public static LocalDateTime date2LocalDataTime(Date date)
    {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @param dateTime 日期
     * @return
     */
    public static Date localDataTime2Date(LocalDateTime dateTime)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * @param date 日期
     * @return
     */
    public static long date2TimeStamp(Date date)
    {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toEpochSecond( OffsetDateTime.now().getOffset());
    }

    /**
     * @param timeStamp 日期
     * @return
     */
    public static Date timeStamp2Date(long timeStamp)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(timeStamp);
        return Date.from(instant);
    }


    /**
     * @param year 年
     * @return 是否为润年
     */
    public static Boolean isLeapYear(int year)
    {
        return year%400 == 0 || (year%100 != 0 && year%4 == 0);
    }

    /**
     * @param timeStamp 时间戳
     * @return 对齐0点的时间戳
     */
    public static long truncDay(long timeStamp)
    {
       return parseStrDate8(strDate8(timeStamp));
    }

    /**
     * @param timeStamp 时间戳
     * @return 对齐0点的时间戳
     */
    public static Date truncDay(Date timeStamp)
    {
        return parseStrDate8ToDate(strDate8(timeStamp));
    }

    /**
     * 计算两个时间戳间隔的天数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 间隔天数
     */
    public static long spanDays(long begin, long end)
    {
        return ((truncDay(end) - truncDay(begin)) / 86400 + 1);
    }

    /**
     * 计算两个时间戳间隔的天数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 间隔天数
     */
    public static long spanDays(Date begin, Date end)
    {
        return ((truncDay(date2TimeStamp(end)) - truncDay(date2TimeStamp(begin)))/ 86400 + 1);
    }


    /**
     * 计算两个时间戳间隔的天数,不包含开始时间戳
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 间隔天数
     */
    public static long spanDaysBeginExclude(long begin, long end)
    {
        return ((truncDay(end) - truncDay(begin)) / 86400 );
    }

    /**
     * 计算两个时间戳间隔的天数,不包含开始时间戳
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 间隔天数
     */
    public static long spanDaysBeginExclude(Date begin, Date end)
    {
        return ((truncDay(date2TimeStamp(end)) - truncDay(date2TimeStamp(begin))) / 86400 );
    }

    /**
     * @param date 时间戳
     * @param days 天数，可以为负数
     * @return 偏移指定天数的时间戳
     */
    public static long addDays(long date,int days )
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(date);
        return LocalDateTime.ofInstant(instant, zone).plusDays(days).atZone(zone).toInstant().toEpochMilli() / 1000;
    }

    /**
     * @param date 时间戳
     * @param days 天数，可以为负数
     * @return 偏移指定天数的时间戳
     */
    public static Date addDays(Date date,int days )
    {
        return localDataTime2Date(date2LocalDataTime(date).plusDays(days));
    }


    /**
     * @param timeStamp 时间戳
     * @return 对齐到当月1号0点的时间戳
     */
    public static long truncMonth(long timeStamp)
    {
        return parseStrDate6(strDate6(timeStamp));
    }

    /**
     * @param timeStamp 时间戳
     * @return 对齐到当月1号0点的时间戳
     */
    public static Date truncMonth(Date timeStamp)
    {
        return parseStrDate6ToDate(strDate6(timeStamp));
    }

    /**
     * @param timeStamp 时间戳
     * @param months 月数
     * @return 偏移指定月数的时间戳
     */
    public static long addMonths( long timeStamp,int months)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochSecond(timeStamp);
        return LocalDateTime.ofInstant(instant, zone).plusMonths(months).atZone(zone).toInstant().toEpochMilli() / 1000;
    }

    /**
     * @param date 时间戳
     * @param months 月数
     * @return 偏移指定月数的时间戳
     */
    public static Date addMonths( Date date,int months)
    {
        return localDataTime2Date(date2LocalDataTime(date).plusMonths(months));
    }

    /**
     * 时间戳对应日期是几号
     * @param timeStamp 时间戳
     * @return 日期
     */
    public static int monthDay(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).getDayOfMonth();
    }

    /**
     * 时间戳对应日期是几号
     * @param date 时间戳
     * @return 日期
     */
    public static int monthDay(Date date)
    {
        return date2LocalDataTime(date).getDayOfMonth();
    }

    /**
     * @param timeStamp 时间戳
     * @param years 偏移几年，可以未负数
     * @return 偏移后的的时间戳
     */
    public static long addYears(long timeStamp, int years)
    {
        return addMonths(timeStamp,years*12);
    }

    /**
     * @param date 时间戳
     * @param years 偏移几年，可以未负数
     * @return 偏移后的的时间戳
     */
    public static Date addYears(Date date, int years)
    {
        return addMonths(date,years*12);
    }

    /**
     * 将形如"YYYYMM"格式的字符串转成时间戳
     * @param strDate 例如:"202001"
     * @return 时间戳
     */
    public static long parseStrDate6(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate+"01000000",str14));
    }

    /**
     * 将形如"YYYYMM"格式的字符串转成时间戳
     * @param strDate 例如:"202001"
     * @return 时间戳
     */
    public static Date parseStrDate6ToDate(String strDate)
    {
        return localDataTime2Date(LocalDateTime.parse(strDate+"01000000",str14));
    }

    /**
     * 将形如"YYYYMMDD"格式的字符串转成时间戳
     * @param strDate 例如:"20200106"
     * @return
     */
    public static long parseStrDate8(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate+"000000",str14));
    }

    /**
     * 将形如"YYYYMMDD"格式的字符串转成时间戳
     * @param strDate 例如:"20200106"
     * @return
     */
    public static Date parseStrDate8ToDate(String strDate)
    {

        return localDataTime2Date(LocalDateTime.parse(strDate+"000000",str14));
    }

    /**
     * 将形如"YYYYMMDDhhmmss"格式的字符串转成时间戳
     * @param strDate 例如:"20200106212350"
     * @return
     */
    public static long parseStrDate14(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate,str14));
    }

    /**
     * 将形如"YYYYMMDDhhmmss"格式的字符串转成时间戳
     * @param strDate 例如:"20200106212350"
     * @return
     */
    public static Date parseStrDate14ToDate(String strDate)
    {

        return localDataTime2Date(LocalDateTime.parse(strDate,str14));
    }

    /**
     * 将形如"YYYY-MM"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01"
     * @return
     */
    public static long parseStrDate7(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate+"-01 00:00:00",str19));
    }

    /**
     * 将形如"YYYY-MM"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01"
     * @return
     */
    public static Date parseStrDate7ToDate(String strDate)
    {

        return localDataTime2Date(LocalDateTime.parse(strDate+"-01 00:00:00",str19));
    }

    /**
     * 将形如"YYYY-MM-DD"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01-06"
     * @return
     */
    public static long parseStrDate10(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate+" 00:00:00",str19));
    }

    /**
     * 将形如"YYYY-MM-DD"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01-06"
     * @return
     */
    public static Date parseStrDate10ToDate(String strDate)
    {

        return localDataTime2Date(LocalDateTime.parse(strDate+" 00:00:00",str19));
    }

    /**
     * 将形如"YYYY-MM-DD hh:mm:ss"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01-06 21:23:50"
     * @return
     */
    public static long parseStrDate19(String strDate)
    {

        return localDataTime2TimeStamp(LocalDateTime.parse(strDate,str19));
    }

    /**
     * 将形如"YYYY-MM-DD hh:mm:ss"格式的字符串转成时间戳
     * @param strDate 例如:"2020-01-06 21:23:50"
     * @return
     */
    public static Date parseStrDate19ToDate(String strDate)
    {

        return localDataTime2Date(LocalDateTime.parse(strDate,str19));
    }


    /**
     * 将时间戳转换成形如"YYYYMM"的字符串
     * @param timeStamp
     * @return 例如:"202001"
     */
    public static String strDate6(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str6);
    }

    /**
     * 将时间戳转换成形如"YYYYMM"的字符串
     * @param timeStamp
     * @return 例如:"202001"
     */
    public static String strDate6(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str6);
    }

    /**
     * 将时间戳转换成形如"YYYYMMDD"的字符串
     * @param timeStamp
     * @return 例如:"20200106"
     */
    public static String strDate8(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str8);
    }

    /**
     * 将时间戳转换成形如"YYYYMMDD"的字符串
     * @param timeStamp
     * @return 例如:"20200106"
     */
    public static String strDate8(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str8);
    }

    /**
     * 将时间戳转换成形如"YYYYMMDDhhmmss"的字符串
     * @param timeStamp
     * @return 例如:"20200106212350"
     */
    public static String strDate14(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str14);
    }

    /**
     * 将时间戳转换成形如"YYYYMMDDhhmmss"的字符串
     * @param timeStamp
     * @return 例如:"20200106212350"
     */
    public static String strDate14(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str14);
    }


    /**
     * 将时间戳转换成形如"YYYY-MM"的字符串
     * @param timeStamp
     * @return 例如:"2020-01"
     */
    public static String strDate7(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str7);
    }

    /**
     * 将时间戳转换成形如"YYYY-MM"的字符串
     * @param timeStamp
     * @return 例如:"2020-01"
     */
    public static String strDate7(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str7);
    }


    /**
     * 将时间戳转换成形如"YYYY-MM-DD"的字符串
     * @param timeStamp
     * @return 例如:"2020-01-06"
     */
    public static String strDate10(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str10);
    }

    /**
     * 将时间戳转换成形如"YYYY-MM-DD"的字符串
     * @param timeStamp
     * @return 例如:"2020-01-06"
     */
    public static String strDate10(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str10);
    }


    /**
     * 将时间戳转换成形如"YYYY-MM-DD hh:mm:ss"的字符串
     * @param timeStamp
     * @return 例如:"2020-01-06 21:23:50"
     */
    public static String strDate19(long timeStamp)
    {
        return timeStamp2LocalDataTime(timeStamp).format(str19);
    }

    /**
     * 将时间戳转换成形如"YYYY-MM-DD hh:mm:ss"的字符串
     * @param timeStamp
     * @return 例如:"2020-01-06 21:23:50"
     */
    public static String strDate19(Date timeStamp)
    {
        return date2LocalDataTime(timeStamp).format(str19);
    }


    /**
     * 将形如"hh:mm:ss"的字符串转成到0点的秒数
     * @param strTime 例如: "21:23:50"
     * @return
     */
    public static int strTime2Second(String strTime)
    {
        LocalTime time =  LocalTime.parse(strTime);
       return time.getHour()*3600 + time.getMinute()*60 + time.getSecond();
    }

    /**
     * 将到0点的秒数转成形如"hh:mm:ss"的字符串
     * @param seconds
     * @return 例如: "21:23:50"
     */
    public static String second2StrTime8(int seconds)
    {
        return LocalTime.of(seconds / 3600, (seconds%3600) /60, (seconds%3600) %60 ).format(strTime8);
    }

    /**
     * 将到0点的秒数转成形如"hhmmss"的字符串
     * @param seconds
     * @return 例如: "212350"
     */
    public static String second2StrTime6(int seconds)
    {
        return LocalTime.of(seconds / 3600, (seconds%3600) /60, (seconds%3600) %60 ).format(strTime6);
    }

    /**
     * 计算当前时间对齐到0点的秒数
     * @return
     */
    public static int nowSecondOfToday()
    {
        LocalTime now = LocalTime.now();
        return now.getHour()*3600+now.getMinute()*60+now.getSecond();
    }

    /**
     * 计算开始时间到结束时间直接的月数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return
     */
    public static int spanMonths(long begin, long end)
    {
        LocalDateTime beginLDT = timeStamp2LocalDataTime(begin);
        LocalDateTime endLDT = timeStamp2LocalDataTime(end);
        return (endLDT.getYear()-beginLDT.getYear()) * 12 + (endLDT.getMonthValue()-beginLDT.getMonthValue())+1;
    }

    /**
     * 计算开始时间到结束时间直接的月数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return
     */
    public static int spanMonths(Date begin, Date end)
    {
        LocalDateTime beginLDT = date2LocalDataTime(begin);
        LocalDateTime endLDT = date2LocalDataTime(end);
        return (endLDT.getYear()-beginLDT.getYear()) * 12 + (endLDT.getMonthValue()-beginLDT.getMonthValue())+1;
    }


    /**
     * 计算开始时间到结束时间直接的月数，不包含开始时间
     * @param begin
     * @param end
     * @return
     */
    public static int spanMonthsBeginExclude(long begin, long end)
    {
        LocalDateTime beginLDT = timeStamp2LocalDataTime(begin);
        LocalDateTime endLDT = timeStamp2LocalDataTime(end);
        return (endLDT.getYear()-beginLDT.getYear()) * 12 + (endLDT.getMonthValue()-beginLDT.getMonthValue());
    }

    /**
     * 计算开始时间到结束时间之间的月数，不包含开始时间
     * @param begin
     * @param end
     * @return
     */
    public static int spanMonthsBeginExclude(Date begin, Date end)
    {
        LocalDateTime beginLDT = date2LocalDataTime(begin);
        LocalDateTime endLDT = date2LocalDataTime(end);
        return (endLDT.getYear()-beginLDT.getYear()) * 12 + (endLDT.getMonthValue()-beginLDT.getMonthValue());
    }
}
