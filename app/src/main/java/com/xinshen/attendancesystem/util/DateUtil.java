package com.xinshen.attendancesystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by thinkpad on 2018/2/6.
 */

public class DateUtil {

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     *将时间戳转换成时间--月：日
     */
    public static String stampToDateNoYear(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDateNoTime(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDateFormat(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     * 获取当前日期前n天（后n天）的日期
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    public static String getTime(long time){
        SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        return dft.format(date);
    }
    /**
     *
     获取指定日期前n天（后n天）的日期
     */
    public static String getOldDate(String currentDate,int distanceDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        try {
            beginDate = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = sdf.parse(sdf.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(endDate);
    }
    /**
     * 获取当前的时间
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentData = new Date();
        String time = sdf.format(currentData);
        return time;
    }
    /**
     * 获取指定规定格式时间的日期
     */
    public static String getDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        String date = "";
        try {
            currentDate = sdf.parse(time);
            date = sdf2.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 获取规定时间格式的时间
     */
    public static String getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        Date currentDate = null;
        String date = "";
        try {
            currentDate = sdf.parse(time);
            date = sdf2.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getYear(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date(time);
        String y = "";
        y = sdf.format(date);
        return Integer.parseInt(y);
    }
    /**
     * 根据时间戳返回时间显示格式---概况界面
     */
    public static String displayTime(long time){
        Calendar current = Calendar.getInstance();
        int nowYear = current.get(Calendar.YEAR);
        int nowDate = current.get(Calendar.DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        long t = calendar.getTimeInMillis();
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.get(Calendar.DATE);

        if (year == nowYear){
            if (date == nowDate){
                return "今天";
            }else if(date == nowDate-1){
                return "昨天";
            }else if(date == nowDate-2){
                return "前天";
            }else{
                return stampToDateNoYear(String.valueOf(time));
            }
        }else{
            return stampToDateNoTime(String.valueOf(time));
        }
    }

    /**
     * 将给点时间戳转化成零点时间戳 00：00：00：
     */
    public static long getZero(long time){
        return time/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
    }

    public static String getDate(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String m = String.valueOf(month);
        String d = String.valueOf(day);
        String w = "";
        if (month<10){
            m = "0"+month;
        }
        if (day<10){
            d = "0"+day;
        }
        switch (week){
            case 1:
                w = "星期一";
                break;
            case 2:
                w = "星期二";
                break;
            case 3:
                w = "星期三";
                break;
            case 4:
                w = "星期四";
                break;
            case 5:
                w = "星期五";
                break;
            case 6:
                w = "星期六";
                break;
            case 7:
                w = "星期日";
                break;
        }
        return year+" 年"+m+" 月"+d+" 日 "+w;
    }


}
