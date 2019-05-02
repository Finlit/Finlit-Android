package com.app.finlit.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class DateHelper {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
    private static String UTC_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static String UTC_DATE_FORMAT_24Hours = "yyyy-MM-dd HH:mm:ss";
    private static final TimeZone utc = TimeZone.getTimeZone("UTC");
    private static final SimpleDateFormat isoFormatter = new SimpleDateFormat(ISO_FORMAT);
    static {
        isoFormatter.setTimeZone(utc);
    }

    private static final String HOURS_12="hh:mm a";
    public static final String CAL_DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    public static SimpleDateFormat TimeFormat12 = new SimpleDateFormat(HOURS_12, Locale.getDefault());
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(CAL_DATE_FORMAT, Locale.getDefault());
    public static SimpleDateFormat dateFormatDashed = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    public static SimpleDateFormat utcDateFormat = new SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault());
    public static SimpleDateFormat utcDateFormat24 = new SimpleDateFormat(UTC_DATE_FORMAT_24Hours, Locale.getDefault());

    public static String getTime(Date date){
        return TimeFormat12.format(date);
    }

    public static String getDate(Date date){
        return dateFormat.format(date);
    }

    public static String getTimeAgo(Date date) {

        long time = date.getTime();

        long now = getCurrentTime();

        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "now";
        } else if (DateUtils.isToday(time)){
            return TimeFormat12.format(date);
        } else if ( date.before(getStartOfDay(now)) && date.after(getYesterdayStartOfDay(now))){
            return "Yesterday";
        } else {
            return dateFormat.format(date);
        }
    }
    public static String utcDateFormated(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        utcDateFormat24.setTimeZone(TimeZone.getTimeZone("UTC"));
        return utcDateFormat24.format(calendar.getTime());
    }

    public static String getUtcFormatInLocalTZ(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return DateHelper.utcDateFormat.format(calendar.getTime());
    }


    public static Date getStartOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYesterdayStartOfDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date localToUTC(Date localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(sdf.parse(sdf.format(localDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }

    public static Date UTCTolocal(Date UTCDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());
        return new Date(TimeFormat12.format(sdf.format(UTCDate)));
    }

    public static boolean isDateBetween(Long start, Long end, Date date){
        Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startDate.setTimeInMillis(start * 1000L);

        Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endDate.setTimeInMillis(end * 1000L);

        return isDateBetween(localToUTC(startDate.getTime()), localToUTC(endDate.getTime()), date);
    }

    public static boolean isDateBetween(Date start, Date end, Date date){

        if(date.after(start) && date.before(end)){
            return true;
        }
        return false;
    }

    public static Date getNextDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getDayAfterTommrow(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        return calendar.getTime();
    }

    public static Date getPreviousDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static Date getCurrentDate(){
        return new Date();
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String splitValues(String s) {
        final String[] separatedTime = s.split("/");
        return separatedTime[0];
    }

    public static String previousWeekString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        return new SimpleDateFormat(CAL_DATE_FORMAT, Locale.US).format(calendar.getTime());
    }

    public static Date parse(String seletedDate) {
        Date date = null;
        try {
            date = utcDateFormat24.parse(seletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFromFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH).format(date);
    }

    public static String getDayOfTheWeek(String seletedDate) {
        Date date = null;
        try {
            date = dateFormat.parse(seletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDayOfTheWeek(date);
    }

    public static String getDayOfTheWeek(Date date) {
        return new SimpleDateFormat("EEE", Locale.getDefault()).format(date);
    }

    public static String toISOString(final Date date) {
        return isoFormatter.format(date);
    }

    public static int minutesDiff(Long earlierDate) {
        return (int)((System.currentTimeMillis()/MINUTE_MILLIS) - (earlierDate/MINUTE_MILLIS));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = getCurrentTime();
        if (time > now || time <= 0) {
            return "just now";
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static Date getDateFromTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getTime();
    }

    public static Date localToGMT(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = new Date(sdf.format(date));
        return gmt;
    }
}
