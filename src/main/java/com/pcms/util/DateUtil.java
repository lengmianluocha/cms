package com.pcms.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Administrator
 */
public class DateUtil {

    public static final String DATE_FOMAT_ONE = "yyyyMMddHHmmss";
    public static final String PATTERN_TIME_STR = "yyyy-MM-dd";
    public static final String PATTERN_TIME_STR1 = "yyyyMMdd";
    public static final String NORMAL_TIME_STR = "yyyy.MM.dd";
    public static final String NORMAL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String Prepay_Date_FORMAT = "yyyyMMdd";

    /**
     * 根据指定日期格式将给出的日期字符串dateStr转换成一个日期对象
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (dateStr == null || dateStr.length() == 0 || pattern == null || pattern.length() == 0) {
            return null;
        }
        DateFormat fmt = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据指定日期格式将给出的日期转换成一个指定的日期字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String parseDate(Date date, String pattern) {
        DateFormat fmt = new SimpleDateFormat(pattern);
        return fmt.format(date);
    }

    /**
     * 将特定格式（yyyy-MM-dd）的字符串转换成日期对象//
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 获取当前的日期
     *
     * @return 当前的日期
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getCurTimestampStr() {
        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        return fmt.format(new Date());
    }

    public static String getCurTimestamp() {
        DateFormat fmt = new SimpleDateFormat(DateUtil.NORMAL_TIME_FORMAT);
        return fmt.format(new Date());
    }

    public static Date getCurTimes() {
        String date = getDate(DateUtil.NORMAL_TIME_FORMAT);
        return parseDate(date, DateUtil.NORMAL_TIME_FORMAT);
    }

    /**
     * 获取日期字符串
     *
     * @param patten 格式化字符串
     * @return
     */
    public static String getDate(String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        return sf.format(new Date());
    }

    public static Date getDate(String date, String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    public static String getDate(Date date, String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        try {
            return sf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCommDateStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    public static String getCommDateStr(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(ts);
    }


    public static String getTimestampString(String date, String hour, String minute) {
        if (date == null || date.trim().length() != 10) {
            date = getDate("yyyy-MM-dd");
        }
        if (hour == null) {
            hour = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (hour.trim().length() < 2) {
            hour = StringUtil.fill(hour, '0', 2, StringUtil.FRONT);
        } else {
            hour = Double.parseDouble(hour) < 24 ? hour.trim().substring(0, 2) : "00";
        }
        if (minute == null) {
            minute = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (minute.trim().length() < 2) {
            minute = StringUtil.fill(minute, '0', 2, StringUtil.FRONT);
        } else {
            minute = Double.parseDouble(minute) < 60 ? minute.trim().substring(0, 2) : "00";
        }
        return date + " " + hour + ":" + minute + ":00.000";
    }

    public static String getTimestampString(String date, String hour, String minute, String second) {
        if (date == null || date.trim().length() != 10) {
            date = getDate("yyyy-MM-dd");
        }
        if (hour == null) {
            hour = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (hour.trim().length() < 2) {
            hour = StringUtil.fill(hour, '0', 2, StringUtil.FRONT);
        } else {
            hour = Double.parseDouble(hour) < 24 ? hour.trim().substring(0, 2) : "00";
        }
        if (minute == null) {
            minute = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (minute.trim().length() < 2) {
            minute = StringUtil.fill(minute, '0', 2, StringUtil.FRONT);
        } else {
            minute = Double.parseDouble(minute) < 60 ? minute.trim().substring(0, 2) : "00";
        }
        if (second == null) {
            second = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (second.trim().length() < 2) {
            second = StringUtil.fill(second, '0', 2, StringUtil.FRONT);
        } else {
            second = Double.parseDouble(second) < 60 ? second.trim().substring(0, 2) : "00";
        }
        return date + " " + hour + ":" + minute + ":" + second + ".000";
    }

    /**
     * 不需要自定义日期，只是通过传入的参数去得到日期
     *
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public static String getTimestampStringWithoutSelfDefine(String date, String hour, String minute) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        if (date == null || date.trim().length() != 10) {
            date = getDate("yyyy-MM-dd");
        }
        if (hour == null) {
            hour = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (hour.trim().length() < 2) {
            hour = StringUtil.fill(hour, '0', 2, StringUtil.FRONT);
        } else {
            hour = Double.parseDouble(hour) < 24 ? hour.trim().substring(0, 2) : "00";
        }
        if (minute == null) {
            minute = StringUtil.fill("", '0', 2, StringUtil.FRONT);
        } else if (minute.trim().length() < 2) {
            minute = StringUtil.fill(minute, '0', 2, StringUtil.FRONT);
        } else {
            minute = Double.parseDouble(minute) < 60 ? minute.trim().substring(0, 2) : "00";
        }
        return date + " " + hour + ":" + minute + ":00.000";
    }

    /**
     * 验证是否为指定日期格式
     *
     * @param str    日期
     * @param format yyyyMMdd
     * @return boolean
     * @Title: isValidDate
     */
    public static boolean isValidDate(String str, String format) {
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写
        if (StringUtils.isBlank(format)) {
            return false;
        }
        boolean validFlg = true;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            sdf.setLenient(false);
            sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            validFlg = false;
        }
        return validFlg;
    }

    /**
     * yyyyMMdd转换为yyyy-MM-dd
     *
     * @param str
     * @return String
     * @Title: formatDate
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String sfStr = "";
        try {
            sfStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sfStr;
    }

    /**
     * 取得当前日期的年
     *
     * @param dataStr 日期字符串
     * @return 年的字符串
     */
    public static String getYear(String dataStr) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parseDate(dataStr));
        return String.valueOf(ca.get(Calendar.YEAR));
    }

    /**
     * 取得当前日期的月
     *
     * @param dataStr 日期字符串
     * @return 月的字符串 注：日期字符串都是2位的,例如: 01,02,03,04,05,06,07,08,09,10,11,12
     */
    public static String getMonth(String dataStr) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parseDate(dataStr));
        String tmpMonthStr = "0" + String.valueOf((ca.get(Calendar.MONTH) + 1));
        return tmpMonthStr.substring(tmpMonthStr.length() - 2);
    }

    /**
     * 取得当前时间的季度
     *
     * @param dataStr 日期字符串
     * @return 季度的字符串 注: 只能是 : 1,2,3,4
     */
    public static String getQuarter(String dataStr) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parseDate(dataStr));
        return String.valueOf(Math.round(ca.get(Calendar.MONTH) / 3 + 1));
    }

    /**
     * 处理传入格式为yyyy-mm-dd-hh.mm.ss.ffffff的字符串
     *
     * @param timestampStr
     * @return
     */
    public static Timestamp getTimestamp(String timestampStr) {
        if (timestampStr != null && !timestampStr.equals("")) {
            return Timestamp.valueOf(timestampStr.substring(0, 10) + " " + timestampStr.substring(11, 13) + ":" + timestampStr.substring(14, 16) + ":" + timestampStr.substring(17));
        } else {
            return null;
        }
    }

    /**
     * 处理传入格式为2009-3-1的数据
     *
     * @param dateStr
     * @return
     */
    public static Timestamp getTimestamp2(String dateStr) {
        if (dateStr.indexOf("-") >= 0) {
            String[] dtAry = dateStr.split("-");
            return Timestamp.valueOf(dtAry[0] + "-" + getFmtTimeStr(dtAry[1]) + "-" + getFmtTimeStr(dtAry[2]) + " 00:00:00.000");
        } else if (dateStr.indexOf("/") >= 0) {
            String[] dtAry = dateStr.split("/");
            return Timestamp.valueOf(dtAry[2] + "-" + getFmtTimeStr(dtAry[1]) + "-" + getFmtTimeStr(dtAry[0]) + " 00:00:00.000");
        }
        return null;
    }

    /**
     * 对于类似于3这类的数据变为03,如果是10,则还是10
     *
     * @return
     */
    private static String getFmtTimeStr(String str) {
        String tmpStr = "00" + str;
        return tmpStr.substring(tmpStr.length() - 2);
    }

    /**
     * 取得当前的日期,不包含时间:2010-12-10 00:00:00
     *
     * @param day 0 当前日期;－1则为当前date的前1天;1则为当前date的后1天
     * @return
     */
    public static Timestamp getCurTimestampNoTime(int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        if (day != 0) {
            c.add(Calendar.DATE, day);
        }
        return new Timestamp(c.getTimeInMillis());
    }

    public static String getSQLTimeOfOracle(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "to_date('".concat(fmt.format(date)).concat("','YYYY-MM-DD HH24:MI:SS')");
    }

    public static String getSQLQLmeOfDb2(Date date) {
        return "'".concat(date.toString()).concat("'");
    }

    //add by chen.biao begin

    /**
     * 查询条件开始日期
     *
     * @return Timestamp
     * @throws ParseException
     * @Title: getStartTime
     */
    public static Timestamp getStartTime(String startTime) {
        if (startTime != null && !startTime.equals("")) {
            if (startTime.indexOf("/") >= 0) {
                startTime = startTime.replaceAll("/", "-");
            }
            startTime = startTime.concat(" 00:00:00.0");
            return Timestamp.valueOf(startTime);
        } else {
            return null;
        }
    }

    /**
     * 查询条件结束日期
     *
     * @return Timestamp
     * @Title: getEndTime
     */
    public static Timestamp getEndTime(String endTime) {
        if (endTime != null && !endTime.equals("")) {
            if (endTime.indexOf("/") >= 0) {
                endTime = endTime.replaceAll("/", "-");
            }
            endTime = endTime.concat(" 23:59:59.999999");
            return Timestamp.valueOf(endTime);
        } else {
            return null;
        }
    }

    /**
     * 查询条件开始日期
     *
     * @param paramMap
     * @param key
     * @return Map<String , Object>
     * @Title: getStartTime
     */
    public static Map<String, Object> getStartTime(Map<String, Object> paramMap, String key) {
        Object obj = paramMap.get(key);
        if (obj != null && obj instanceof String) {
            String startTime = (String) paramMap.get(key);
            if (StringUtils.isNotBlank(startTime)) {
                startTime = startTime.replaceAll("/", "-");
                startTime = startTime.concat(" 00:00:00.0");
                Timestamp timestamp = Timestamp.valueOf(startTime);
                paramMap.put(key, timestamp);
            }
        }
        return paramMap;
    }

    /**
     * 查询条件结束日期
     *
     * @param paramMap
     * @param key
     * @return Map<String , Object>
     * @Title: getEndTime
     */
    public static Map<String, Object> getEndTime(Map<String, Object> paramMap, String key) {
        Object obj = paramMap.get(key);
        if (obj != null && obj instanceof String) {
            String endTime = obj.toString();
            if (StringUtils.isNotBlank(endTime)) {
                endTime = endTime.replaceAll("/", "-");
                endTime = endTime.concat(" 23:59:59.999999");
                Timestamp timestamp = Timestamp.valueOf(endTime);
                paramMap.put(key, timestamp);
            }
        }
        return paramMap;
    }

    /**
     * 2013-7-9格式   转换成   2013-07-09格式
     */
    public static String getNewFormatStr(String str) {
        if (str != null && !str.equals("")) {
            if (str.indexOf("/") >= 0) {
                str = str.replaceAll("/", "-");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                String year = getYear(str);
                if (year != null && year.length() < 4) {
                    SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd", Locale.CHINA);
                    date = sf.parse(str);
                } else {
                    date = sdf.parse(str);
                }
                str = sdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return str;
        } else {
            return null;
        }
    }
    //add by chen.biao end


    /**
     * String转换为Timestamp
     */
    public static Timestamp toTimestamp(String time) {
        if (StringUtils.isNotBlank(time)) {
            //DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return new Timestamp(fmt.parse(time).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取当前日期   多少月后的日期
     *
     * @param month
     * @return Timestamp
     * @Title: getAfertMonth
     */
    public static Timestamp getAfertMonth(Integer month) {
        Calendar cal = Calendar.getInstance();
        // 下面的就是把当前日期加一个月
        cal.add(Calendar.MONTH, month);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 字符串转时间    例："20151213"转"2015-12-13"
     * 解释客户端字符串时间不规则  需要转成指定的规则再通过"yyyy-MM-dd "转换时间
     */
    public static String ConverToDate(String time) throws ParseException {
        if (time == "" || null == time)
            return null;
        if (time.length() == 8) {
            return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        } else {
            return "";
        }
    }


    /**
     * 日期比较  1:大于   2：小于   0:相等
     *
     * @param startTime
     * @param endTime
     * @return int
     * @Title: compareDate
     */
    public static int compareDate(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(startTime);
            Date dt2 = df.parse(endTime);
            if (dt1.getTime() > dt2.getTime()) {
                //dt1 在dt2前
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //dt1在dt2后
                return 2;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 接口转换
     *
     * @param date
     * @return String
     * @Title: parseDate
     */
    public static String toStr(Date date) {
        return parseDate(date, "yyyyMMdd");
    }


    /**
     * 获取当前时间点的超时时间，分钟为单位
     *
     * @param min
     * @return
     */
    public static String getTimeOut(int min) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FOMAT_ONE);
        long time = min * 60 * 1000;//30分钟
        Date afterDate = new Date(new Date().getTime() + time);
        String timeout = sdf.format(afterDate);
        return timeout;
    }

    /**
     * 判断时间是否在两者之间
     *
     * @param date  时间
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    public static Boolean timeIsBetween(Date date, String begin, String end, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date beginDate = simpleDateFormat.parse(begin);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(DATE_FOMAT_ONE);
            Date endDate = simpleDateFormat1.parse(end.concat("235959"));
            if (date.after(beginDate) && date.before(endDate)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
