/**
 *
 */
package cn.richinfo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class DateUtil {

    /**
     * <title>getStringToDate</title>
     *
     * @param date
     * @return Date
     */
    public static Date getStringToDate(String date) {
        try {
            if (null != date) {
                return DateUtil.getStrToDate(date);
            } else {
                return DateUtil.getPreviousDayDate();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getDateToString
     *
     * @param _value  Date
     * @param formate String
     * @return String
     */
    public static String getDateToString(Date _value, String formate) {
        if (null == formate) formate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(_value);
    }

    /**
     * getDateToString
     *
     * @param _value  Date
     * @param formate String
     * @return String
     */
    public static String getDateToString(Date _value) {
        return getDateToString(_value, null);
    }

    /**
     * <p>
     * Date转时间型String
     * </p>
     *
     * @param _value  Date
     * @param formate String
     * @return _value String
     */
    public static String getDateTimeString(Date _value, String formate) {
        if (null == formate) formate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(_value);
    }

    /**
     * 获取今天日期
     *
     * @return
     */
    public static String getNowDateStr() {
        String formate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(new Date());
    }

    /**
     * 从long型日期转String类型
     *
     * @param date
     * @param formate
     * @return String
     */
    public static String getDateFromLong(long date, String formate) {
        Date _date = new Date(date);
        if (null == formate) formate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(_date);
    }

    /**
     * getDate
     *
     * @param _value
     * @return
     */
    public static Date getDate(Date _value) {
        String time = getDateTimeString(_value, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * <title>获取前一天的日期</title>
     * <code>getPreviousDayDate("yyyy-MM-dd")</code>
     *
     * @return Date date
     */
    public static Date getPreviousDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        return date;
    }

    /**
     * <title>获取前N天的日期</title>
     * <code>getPreviousDayDate("yyyy-MM-dd")</code>
     *
     * @return Date date
     */
    public static Date getPreviousDayDate(int pre) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(pre)); //得到前一天
        Date date = calendar.getTime();
        return date;
    }

    /**
     * getStrToDate
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date getStrToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);
        return date;
    }

    /**
     * @param dateString 字符日期格式
     * @param format     转换的格式
     * @return
     * @throws ParseException
     */
    public static Date getStrToDateFormat(String dateString, String format) throws ParseException {
        if (format == null) {
            format = "yyyy-MM-dd hh:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateString);
        return date;
    }


    /**
     * 获取指定日期(毫秒格式)
     */
    public static String getMillFormatDateTime(Date _value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(_value);
    }

    /**
     * 获取指定时间戳(毫秒格式)
     */
    public static String getMillFormatDateTime(long _value) {
        return getMillFormatDateTime(new Date(_value));
    }

    /**
     * 获取当前时间戳(毫秒格式)
     */
    public static String getMillFormatDateTime() {
        return getMillFormatDateTime(new Date());
    }

    /**
     * 根据秒获取时分
     *
     * @param second 秒
     * @return String
     */
    public static String getHMBySecond(int second) {
        if (second / 3600 >= 1) {
            int h = second / 3600;
            int m = second % 3600 / 60;
            int s = second % 60;
            return "" + h + "时" + (m > 0 ? "" + m + "分" : "") + (s > 0 ? "" + s + "秒" : "");
        } else {
            int m = second % 3600 / 60;
            int s = second % 60;
            return m + "分" + (s > 0 ? "" + s + "秒" : "");
        }
    }

    /**
     * 计算两个日期之间相差的秒数
     *
     * @return 相差秒数
     * @throws ParseException
     */
    public static int SecondsBetween(String starttime, String endtime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startdate;
        try {
            startdate = sdf.parse(starttime);
            Date enddate = sdf.parse(endtime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(enddate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / 1000;
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

}
