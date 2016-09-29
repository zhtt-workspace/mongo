package zhtt.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhtt on 2016/9/19.
 */
public class CalendarHelp {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String EN_YMDHMS="yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String EN_YMDHM="yyyy-MM-dd HH:mm";

    /**
     * yyyy-MM-dd
     */
    public static final String EN_YMD="yyyy-MM-dd";

    /**
     * yyyy-M-d
     */
    public static final String EN_YMD_="yyyy-M-d";

    /**
     * yyyy年M月d日
     */
    public static final String CN_YMD="yyyy年M月d日";

    /**
     * HH时mm分
     */
    public static final String CN_HM="HH时mm分";

    /**
     * String 转 Date
     * @param dateStr
     * @param format
     * @return
     */
    public static Date format(String dateStr,String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Date 转 String
     * @param date
     * @param formatStr
     * @return
     */
    public static String format(Date date,String formatStr){
        if(date==null){
            return null;
        }
        try {
            DateFormat format = new SimpleDateFormat(formatStr);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Date 转 String
     * @param date
     * @return
     */
    public static String format(Date date){
        if(date==null){
            return null;
        }
        try {
            DateFormat format = new SimpleDateFormat(EN_YMDHMS);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到当天日期
     * @return
     */
    public static String getTodayDateStr(){
        return format(new Date(),EN_YMD);
    }

    /**
     * 某天开始时间
     * @return
     */
    public static Date formatDayStartTime(Date date){
        return format( format(date, EN_YMD)+ " 00:00:00", EN_YMD);
    }

    /**
     * 某天开始时间
     * @return
     */
    public static Date formatDayStartTime(String date){
        return format( date + " 00:00:00", EN_YMDHMS);
    }

    /**
     * 某天结束时间
     * @return
     */
    public static Date formatDayEndTime(Date date){
        return format( format(date, EN_YMD)+ " 23:59:59", EN_YMD);
    }

    /**
     * 某天开始时间
     * @return
     */
    public static Date formatDayEndTime(String date){
        return format( date + " 23:59:59", EN_YMDHMS);
    }
}
