package com.spider.cartoon.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;


//import org.apache.commons.lang3.time.DateUtils;

public class DatetimeUtilies {
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String DATE_TIME_MIN = "yyyy-MM-dd HH:mm";

    public static final String DATE = "yyyy/MM/dd";
    
    public static final String DOT_DATE = "yyyy.MM.dd";

    public static final String TIME = "HH:mm:ss";

    public static final String DATE_HOUR = "yyyy-MM-dd HH";

    public static final String DATE_SHORT = "yyMMdd";

    public static final String YEAR = "yyyy";
    
    public static final String DATE_CHINESE = "yyyy年MM月dd日";

    public static final String DAILY_COMMENT_NOTIFY = "yyyy年MM月";
    
    public static final int FIRST_DAY = Calendar.MONDAY;
    /**
     * 获取当前时间秒数
     * 
     * @return String
     */
    public static String getCurrTime10() {
        return "" + System.currentTimeMillis() / 1000;
    }

    /**
     * 检查给定的日期是否在两个日期中间
     *
     * @param current
     *            compare date
     * @param min
     *            min date
     * @param max
     *            max date
     * @return if between min date and max date, then return true.
     */
    public static boolean between(Date current, Date min, Date max) {
        return current.after(min) && current.before(max);
    }

    /**
     * 计算给定月分的自然周划分区间段（可能会上下月交界）为5周。
     *
     * @param month
     *            指定的月份
     * @return weeks array. for example, ['03.01-03.07', '03.08-03.14']
     */
    public static List<String> getMonthWeeks(int month) {
        SimpleDateFormat df = new SimpleDateFormat("MM.dd");
        List<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < 5; i++) {
            Date d = calendar.getTime();
            String dt = df.format(d);
            d = addDays(d, 6);
            dt += "-" + df.format(d);
            calendar.setTime(addDays(d, 1));
            list.add(dt);
        }
        return list;
    }
    public static Date addDays(final Date date, final int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }
    private static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    /**
     * return current time by timestamp.
     *
     * @return
     */
    public static Timestamp currentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     *
     * @return
     */
    public static Date currentDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     *
     * @param date
     * @return
     */
    public static boolean beforeToday(Date date) {
        Date today = currentDay();
        return date.before(today);
    }

    /**
     * get the days between give date range.
     *
     * @param from
     *            begin date
     * @param to
     *            end date
     * @return days
     */
    public static int daysBetween(Date from, Date to) {
        long t = to.getTime() - from.getTime();
        return Integer.valueOf(t / 1000 / 60 / 60 / 24 + "");
    }

    /**
     * return current week range.
     *
     * @return [0] begin date, [1] end date
     */
    public static Date[] getCurrentWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date1 = cal.getTime();
        Date date2 = addDays(date1, 6);
        return new Date[] { date1, date2 };
    }

    /**
     * return current month range.
     *
     * @return [0] begin date, [1] end date
     */
    public static Date[] getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date date1 = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date2 = cal.getTime();
        return new Date[] { date1, date2 };
    }

    /**
     * get current quarter.
     *
     * @return quarter number
     */
    public static int getCurrentQuarter() {
        Calendar calendar = Calendar.getInstance();
        int mh = calendar.get(Calendar.MONTH) + 1;
        int qr = (mh - (mh - 1) % 3 + 2) / 3;
        return qr;
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateEquals(Date date1, Date date2) {
        boolean eq = true;
        if ((date1 != null && date2 == null) || (date2 != null && date1 == null)) {
            eq = false;
        } else if (date1 != null && date2 != null) {
            eq = date1.getTime() == date2.getTime();
        }
        return eq;
    }

    /**
     * 获取本地时间的当前时间戳.<br>
     * 1. 时间戳格式为：yyyyMMddHHmmss<br>
     * 
     * @return 时间戳字符串
     * @author LiCunjing
     */
    public static String getCurTimestampStr() {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS);

        return formatter.format(new Date());
    }

    // 通过时间格式格式化时间
    public static String formatDateTime(String pattern, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        return formatter.format(date);
    }

    /**
     * 当前时间加上固定秒数<br>
     * 
     * @return 时间
     * @author zhangchangshun
     */
    public static Date addSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 将指定格式的时间字符串转为Date对象<br>
     * 1. timeString的格式必须符合pattern，否则抛出异常。<br>
     * 
     * @param pattern
     *            时间格式
     * @param timeString
     *            时间字符串
     * @return Date类型时间
     * @throws ParseException
     *             时间字符串解析失败，pattern与timeString不符
     * @author LiCunjing
     */
    public static Date parse(String pattern, String timeString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(timeString);
    }

    /**
     * 计算当天剩余秒数
     * 
     * @return
     */
    public static int getRemainSeconds() {
        Calendar calendar = Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long endTime = calendar.getTimeInMillis();
        int time = (int) ((endTime - startTime) / 1000);
        return time;
    }

    /**
     * 获取系统指定天数之前的时间
     * 
     * @author Oliver
     * @param days
     * @return
     */
    public static Date getSpecifyDaysAgo(int days) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }

    /**
     * 获取系统指定天数之前的时间
     * 
     * @author Oliver
     * @param days
     * @return
     */
    public static Date getSpecifyDaysAgo(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }
    
    
    /**
     * 获得某天的零点时刻0:0:0
     * 
     * @param date
     *            日期
     * @return
     */
    public static Date getDayBegin(Date date) {

        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得某天的截至时刻23:59:59
     * 
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {

        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        // 当毫秒为999时，数据库秒会加1
        cal.set(Calendar.MILLISECOND, 1);
        return cal.getTime();
    }

    /**
     * 2个时间之间的时间差
     * 
     * @author xiaotao
     * @param DateTime1
     * @param DateTime2
     * @return
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }

    /**
     * 获得当月的初始时刻
     * 
     * @param date
     *            日期
     * @return
     */
    public static Date getMonthBegin(Date date) {

        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获得当年的初始时刻
     * 
     * @param date
     *            日期
     * @return
     */
    public static Date getYearBegin(Date date) {

        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 获取两个日期之间的日期
     * 
     * @param start
     *            开始日期
     * @param end
     *            结束日期
     * @return 日期集合
     */
    public static List<String> getBetweenDates(Date start, Date end) {
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (!tempStart.after(tempEnd)) {
            result.add(formatDateTime(DATE, tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 
     * @author jiangxia
     * @param date
     * @return
     */
    public static Date[] getCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date date1 = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date2 = cal.getTime();
        return new Date[] { date1, date2 };
    }

    /**
     * 根据月考核周期计算考核表的起始时间和结束时间
     * 
     * @author jiangxia
     * @return
     */
    public static Date[] getTimeByPeriodByMonth(Date date) {
        Date[] result = DatetimeUtilies.getCurrentMonth(date);
        result[1] = DatetimeUtilies.getDayEnd(result[1]);
        return result;
    }

    /**
     * 获取上个月的时间
     * 
     * @author jiangxia
     * @param date
     * @return
     */
    public static Date getLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获得某天的下午6点
     * 
     * @param date
     *            日期
     * @return
     */
    public static Date getDayAt18(Date date) {

        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    

    /**
     * 获取当前时间一周的日期
     */
    public static List<Date> weekDays() {
        List<Date> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (calendar.getFirstDayOfWeek() == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            list.add(calendar.getTime());
        }
        return list;
    }

    /**
     * 
     * @param args
     */
    public static Date nextMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    
    public static Date getMaxDate(Collection<Date> dates)
    {
        if(CollectionUtils.isEmpty(dates))
        {
            return null;
        }
        Iterator<Date> iter = dates.iterator();
        Date init = null;
        while(iter.hasNext())
        {
            Date temp = (Date) iter.next();
            if(init == null)
            {
                init = temp;
            }
            else
            {
                init = temp.compareTo(init)>0?temp:init;
            }
        }
        return init;
    }
    
    
 
    
    /**
     * 获取系统指定天数之前的时间
     * 
     * @author Oliver
     * @param days
     * @return
     */
    public static Date getSpecifyMonthsLater(int month, int day) {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.MONTH, month);
        c.add(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取系统指定天数之前的时间
     * 
     * @author Oliver
     * @param days
     * @return
     */
    public static Date getSpecifyMonthsLater(Date input, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        
        c.add(Calendar.MONTH, month);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    /**
     * 昨天的日期
     * @author xiaotao
     * @return
     */
    public static Date getYesterday(Date currentDate){
        
        Calendar cal=Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        return time;
    }
    
    
    /**
     * 获取年
     * @author xiaotao
     * @return
     */
    public static int getYear(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        
        return cal.get(Calendar.YEAR);
    }
    
    public static void main(String[] args) throws ParseException 
    {
//        System.out.println(DatetimeUtilies.formatDateTime(DatetimeUtilies.DATE_CHINESE, new Date()));
//        System.out.println(11/3);
//        System.out.println(getSpecifyDaysAgo(-30));
//        
//        System.out.println(DatetimeUtilies.getSpecifyMonthsLater(new Date(), 12));
//        Date date = DatetimeUtilies.parse(DatetimeUtilies.DATE, "2017-08-1");
        Date to = new Date();
        to = getDayBegin(to);
        
        Date from = getSpecifyDaysAgo(to, -15);
        from = getDayBegin(from);
        System.out.println(from);
        System.out.println(to);
        System.out.println(daysBetween(from, to));
        
        
        Date to1 = new Date();
        System.out.println(DatetimeUtilies.daysBetween(to, to1));
        
        
        int t = -60;
        int aa = (t /60 );
        System.out.println(aa);
        
        String as= "123456";
        System.out.println(as.substring(3));
        
        System.out.println (DatetimeUtilies.formatDateTime(DatetimeUtilies.YYYYMMDDHHMMSS, new Date()));
        
       
        List<String> aaaaa = Lists.newArrayList();
        aaaaa.add(null);
        aaaaa.add("111");
        aaaaa.add(null);
        System.out.println(aaaaa.indexOf(null));
        
        
        List<String> list = Lists.newArrayList(
                "bcd", "cde", "def", "abc");
        
        for(String aaxa:list)
        {
            aaxa = aaxa+"1";
        }
        BigDecimal ab= new BigDecimal(79.7);
        BigDecimal ac= new BigDecimal(20.3);
        ab = ab.add(ac).setScale(2, RoundingMode.HALF_UP);
        System.out.println(ab);
        
        
        Date c = DatetimeUtilies.parse(DatetimeUtilies.DATE, "2018-05-31");
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c);
        
    }
}
