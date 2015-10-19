package com.changhong.common.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:18
 */
public class JodaUtils {
    public static final String DEFAUT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String DMYHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DMYHM = "yyyy-MM-dd HH:mm";

    public static DateTime now() {
        return new DateTime();
    }

    public static LocalDate today() {
        return new LocalDate();
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static DateTime parseDateTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAUT_DATE_FORMAT);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyy-MM
     * @param date
     * @return
     */
    public static DateTime parseDateTimeYearMonth(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_FORMAT);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    public static DateTime parseDateTimedMyHM(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DMYHM);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static DateTime parseDateTimedMyHms(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DMYHMS);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param date
     * @param pattern
     * @return
     */
    public static DateTime parseDateTime(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param dateAsText
     * @param pattern
     * @return
     */
    public static LocalDate parseLocalDate(String dateAsText, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(dateAsText);
            return new LocalDate(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyy-MM-dd
     * @param dateAsText
     * @return
     */
    public static LocalDate parseAsLocalDate(String dateAsText) {
        return parseLocalDate(dateAsText, DEFAUT_DATE_FORMAT);
    }

    /**
     * yyyy-MM
     * @param date
     * @return
     */
    public static DateTime parseDateTimeToMonthYear(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_FORMAT);
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            return new DateTime(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public static DateTime currentTime() {
        return new DateTime();
    }

    /**
     *
     * @param date
     * @return
     */
    public static String toString(LocalDate date) {
        return date.toString(DEFAUT_DATE_FORMAT);
    }

    /**
     *
     * @param date
     * @param parttern
     * @return
     */
    public static String toString(LocalDate date, String parttern) {
        return date.toString(parttern);
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String toString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAUT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String toFullString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DMYHM);
        return simpleDateFormat.format(date);
    }

    public static String toString(DateTime dateTime) {
        return dateTime.toString(DEFAUT_DATE_FORMAT);
    }

    /**
     * DMYHM yyyy-MM-dd HH:mm:ss
     */
    public static String toStringDMYHMS(DateTime dateTime) {
        return dateTime.toString(DMYHMS);
    }

    public static String toStringYearMonth(DateTime dateTime) {
        return dateTime.toString(YEAR_MONTH_FORMAT);
    }

    /**
     * yyyy-MM-dd HH:mm
     * @param dateTime
     * @return
     */
    public static String toStringMyHM(DateTime dateTime) {
    	return dateTime.toString(DMYHM);
    }
    /*
     *
     */
    public static String toStringYearMonth(DateTime dateTime, String parttern) {
        return dateTime.toString(parttern);
    }

    /**
     * yyyy
     * @param dateTime
     * @return
     */
    public static String toYearString(DateTime dateTime) {
        return dateTime.toString("yyyy");
    }

    /**
     * HH
     * @param dateTime
     * @return
     */
    public static String toHourString(DateTime dateTime) {
		return dateTime.toString("HH");

    }

    /**
     * mm
     * @param dateTime
     * @return
     */
    public static String toMinuteString(DateTime dateTime) {
    	return dateTime.toString("mm");
    }

    /**
     * dd-MM-yyyy HH:mm
     * @param dateTime
     * @return
     */
    public static String todMYHmString(DateTime dateTime) {
        return dateTime.toString("dd-MM-yyyy HH:mm");
    }

    /**
     *
     * @return
     */
    public static DateTime beginDateOfWeek() {
        LocalDate current = new LocalDate();
        int dayOfWeek = current.getDayOfWeek();
        current = current.minusDays(dayOfWeek - 1);
        return current.toDateTimeAtMidnight();
    }

    /**
     *
     * @return
     */
    public static DateTime endDateOfWeek() {
        LocalDate current = new LocalDate();
        int dayOfWeek = current.getDayOfWeek();
        current = current.plusDays(7 - dayOfWeek);
        return current.toDateTimeAtMidnight();
    }

    /**
     *
     * @return
     */
    public static DateTime getFirstDateOfWeek() {
        DateTime current = new DateTime();
        int weekDay = current.getDayOfWeek();
        return current.plusDays((weekDay - 1) * -1);
    }

    /**
     *
     * @return
     */
    public static DateTime getEndDateOfWeek() {
        DateTime current = new DateTime();
        int weekDay = current.getDayOfWeek();
        return current.plusDays((7 - weekDay));
    }

    /**
     *
     * @return
     */
    public static LocalDate getFirstDateOfMonth() {
        LocalDate current = new LocalDate();
        int year = current.getYear();
        int month = current.getMonthOfYear();
        return new LocalDate(year, month, 1);
    }

    /**
     *
     * @return
     */
    public static LocalDate getFirstDateOfNextMonth() {
        LocalDate current = new LocalDate();
        int year = current.getYear();
        int month = current.getMonthOfYear();
        return new LocalDate(year, month + 1, 1);
    }

    /**
     *
     * @param year
     * @param month
     * @return
     */
    public static int getCurrentMonthTotalDays(int year, int month) {
        String current = new LocalDate().toString("YYYY-MM");
        LocalDate temp = new LocalDate(current + "-01").plusMonths(1).minusDays(1);
        return temp.getDayOfMonth();
    }
}
