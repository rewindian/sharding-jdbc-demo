package io.ian.demo.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {


    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }


    /**
     * 获取MM格式
     *
     * @return
     */
    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }


    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return formatDate(new Date(), "yyyyMMdd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getMsTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description:(日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (parseDate(s) == null || parseDate(e) == null) {
            return false;
        }
        return parseDate(s).getTime() >= parseDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseMsTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 格式化Oracle Date
     * @param value
     * @return
     */
//	public static String buildDateValue(Object value){
//		if(Func.isOracle()){
//			return "to_date('"+ value +"','yyyy-mm-dd HH24:MI:SS')";
//		}else{
//			return Func.toStr(value);
//		}
//	}


    /**
     * @return 今天 16:00
     * 昨天 16:00
     * 前天 16:00
     * 2015-10-26 16:00
     */
    public static String formatDateTime_今天昨天前天(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (date == null) {
            return "";
        }
        String time;
        try {
            time = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        Calendar current = Calendar.getInstance();

        Calendar thisTime = Calendar.getInstance();
        thisTime.setTime(date);

        if (current.get(Calendar.YEAR) != thisTime.get(Calendar.YEAR)) {
            //不是同一年了

            return DateUtils.formatDate(date, pattern);
        }

        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar beforeYesterday = Calendar.getInstance();    //前天

        beforeYesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        beforeYesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        beforeYesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 2);
        beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
        beforeYesterday.set(Calendar.MINUTE, 0);
        beforeYesterday.set(Calendar.SECOND, 0);

        if (thisTime.after(today)) {
            return time.split(" ")[1];
        } else if (thisTime.after(yesterday)) {
            return "昨天" + time.split(" ")[1];
        } else if (thisTime.after(beforeYesterday)) {
            return "前天" + time.split(" ")[1];
        } else {
            int index = time.indexOf("-") + 1;
            return time.substring(index, time.length());
        }
    }


    /**
     * 获取当前周第一天,中国习惯,若查询日期是周日,则显示这周一,而非下周一
     *
     * @return
     * @time 2019年4月28日
     */
    public static Date getWeekStartNew() {
        Calendar cal = Calendar.getInstance();
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return date;
    }

    /**
     * 后一天
     *
     * @param date
     * @param day
     * @return
     * @author jclyzx
     * @time 2018年6月7日
     */
    public static String getNextDay(String date, int day) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, day);
        // Date date1 = calendar.getTime();
        Date time = calendar.getTime();
        return format.format(time);
    }

    public static Map<String, Date> getMonthRange(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date edtm = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date bgtm = calendar.getTime();
        Map<String, Date> map = new HashMap<>();
        map.put("bgtm", bgtm);
        map.put("edtm", edtm);
        return map;
    }

    public static Date getMongoDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 8);
        return cal.getTime();
    }

    public static String appendLastSecondOfDay(String yyyyMMdd) {
        return yyyyMMdd + " 23:59:59";
    }

    public static Date getEndTimeOfDay(String edtm) {
        return parseTime(appendLastSecondOfDay(edtm));
    }

    public static void main(String[] args) {
//		Date date=getWeekStartNew();
//		String time=formatDate(date,null);
//		String start=getNextDay(time,-7);
//		String end=getNextDay(time,-1);
//
//		System.out.println(time);
//		System.out.println(start);
        System.out.println("wr43wrt5545786790865676890g4esrgerg".hashCode() % 7);
    }

}
