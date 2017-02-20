package org.ehais.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author lgj628
 *
 */
public class DateUtil {

	/**
	 * yyyy-MM-dd HH:mm:ss SSS
	 */
	public static String FORMATSTR_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String FORMATSTR_2 = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * yyyy-MM-dd
	 */
	public static String FORMATSTR_3 = "yyyy-MM-dd";
	
	/**
	 * yyyyMMddHHmmss
	 */
	public static String FORMATSTR_4 = "yyyyMMddHHmmss";
	
	/**
	 * yyyymmdd
	 */
	public static String FORMATSTR_5 = "yyyyMMdd";
	
	/**
	 * yyyy-MM
	 */
	public static String FORMATSTR_6 = "yyyy-MM";
	
	
	
	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date formatDate(String dateTimeStr, String formatStr) {
		try {
			if (dateTimeStr == null || dateTimeStr.equals("")) {
				return null;
			}
			DateFormat sdf = new SimpleDateFormat(formatStr);
			java.util.Date d = sdf.parse(dateTimeStr);
			return d;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	/**
	 * 将Date转换成formatStr格式的字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(Date date, String formatStr) {
		if(date == null || formatStr == null || formatStr.equals(""))return "";
		DateFormat df = new SimpleDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * 获取当前时间的字符串
	 * @param formatStr
	 * @return
	 */
	public static String getCurDate(String formatStr) {
		return formatDate(Calendar.getInstance().getTime(),formatStr);
	}



	/**
	 * 获取日期d的days天后的一个Date
	 * @param d
	 * @param days
	 * @return
	 */
	public static Date getInternalDateByDay(Date d, int days) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}
    /**
     * 
     * @param d
     * @param months
     * @return
     */
	public static Date getInternalDateByMon(Date d, int months) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MONTH, months);
		return now.getTime();
	}
    /**
     * 
     * @param d
     * @param years
     * @return
     */
	public static Date getInternalDateByYear(Date d, int years) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.YEAR, years);
		return now.getTime();
	}
    /**
     * 
     * @param d
     * @param sec
     * @return
     */
	public static Date getInternalDateBySec(Date d, int sec) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.SECOND, sec);
		return now.getTime();
	}
    /**
     * 
     * @param d
     * @param min
     * @return
     */
	public static Date getInternalDateByMin(Date d, int min) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MINUTE, min);
		return now.getTime();
	}
    /**
     * 
     * @param d
     * @param hours
     * @return
     */
	public static Date getInternalDateByHour(Date d, int hours) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.HOUR_OF_DAY, hours);
		return now.getTime();
	}

	

	/**
	 * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long compareDateStr(String time1, String time2) {
		Date d1 = formatDate(time1,FORMATSTR_2);
		Date d2 = formatDate(time2,FORMATSTR_2);
		return d2.getTime() - d1.getTime();
	}
	
	/**
	 * 获取两日期相差天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);
        if(c1.after(c2)){ 
    		Calendar cal=c1; 
    		c1=c2; 
    		c2=cal; 
    	} 
        
		//分别得到两个时间的毫秒数 
		long sl=c1.getTimeInMillis(); 
		long el=c2.getTimeInMillis(); 

		long ei=el-sl; 
		//根据毫秒数计算间隔天数 
		return (int)(ei/(1000*60*60*24));
		
		
	}
	
/*
	public int getIntervalDays(Calendar startday,Calendar endday)...{ 
		//确保startday在endday之前 
		if(startday.after(endday))...{ 
		Calendar cal=startday; 
		startday=endday; 
		endday=cal; 
		} 
		//分别得到两个时间的毫秒数 
		long sl=startday.getTimeInMillis(); 
		long el=endday.getTimeInMillis(); 

		long ei=el-sl; 
		//根据毫秒数计算间隔天数 
		return (int)(ei/(1000*60*60*24)); 
		} 
*/
	
    public static int diffMonth(Date date1, Date date2) throws ParseException {

        int result = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result == 0 ? 0 : Math.abs(result);

    }
    public static int diffMonth(String date1, String date2) throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result == 0 ? 0 : Math.abs(result);

    }

	/**
	 * 获取Date中的分钟
	 * @param d
	 * @return
	 */
	public static int getMin(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 获取Date中的小时(24小时)
	 * @param d
	 * @return
	 */
	public static int getHour(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取Date中的秒
	 * @param d
	 * @return
	 */
	public static int getSecond(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.SECOND);
	}

	
	/**
	 * 获取月份，1-12月
	 * 
	 * @param d
	 * @return
	 */
	public static int getMonth(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MONTH) + 1;
	}

    /**
	 * 转换日期
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(String date,String formatStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Calendar cld = Calendar.getInstance();
		try {
			cld.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cld;
	}
	
	/**日期比较
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
	        if (dt1.getTime() > dt2.getTime()) {
	            return 1;
	        } else if (dt1.getTime() < dt2.getTime()) {
	            return -1;
	        } else {
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
	
	public static int compare_date(Date dt1, Date dt2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        
	        if (dt1.getTime() > dt2.getTime()) {
	            return 1;
	        } else if (dt1.getTime() < dt2.getTime()) {
	            return -1;
	        } else {
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
	
	/**
	 * 获取月的全部日期
	 * @param dBegin 当前月的1号
	 * @param dEnd 当前月的最后一天
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List lDate = new ArrayList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	/**
	 * 时间格式判断
	 * @param time 时间 08:15
	 * @return
	 */
	public static boolean timeFormat(String timeStr) {
		String eL = "([0-1]?[0-9]|2[0-3]):([0-5][0-9])";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(timeStr);
		boolean dateFlag = m.matches();
		if (!dateFlag) {
			System.out.println("格式错误");
			return false;
		}
		System.out.println("格式正确");
		return true;
	}
	
	
	public static Date getLastDay(Date date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天

        String day_last = df.format(calendar.getTime());//最后一天

		return df.parse(day_last);
		
	}
	

	public static Date addDate(Date date,int day) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        String day_last = df.format(calendar.getTime());//最后一天
		return df.parse(day_last);
	} 
	
	public static Date addMonth(Date date,int month) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        String day_last = df.format(calendar.getTime());//最后一天
		return df.parse(day_last);
	} 
	
	public static Date addYear(Date date,int year) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        String day_last = df.format(calendar.getTime());//最后一天
		return df.parse(day_last);
	} 
	
	
	
}
