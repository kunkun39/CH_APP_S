package com.changhong.common.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:37
 */
public class CHDateUtils {

    public static String getSimpleDateFormat(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");
        String formatString=time.format(date);
        return formatString;

    }

    public static String getFullDateFormat(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatString=time.format(date);
        return formatString;

    }

    public static int getCurrentYear() {
        Date current = new Date();
        return current.getYear() + 1900;
    }

    public static int getCurrentMonth() {
        Date current = new Date();
        return current.getMonth() + 1;
    }

    public static int getCurrentDate() {
        Date current = new Date();
        return current.getDate();
    }

    public static int getCurrentHours(){
        Date current = new Date();
        return current.getHours();
    }


    public static Date getFirstDateOfMonth() {
        Date current = new Date();
        int year = current.getYear();
        int month = current.getMonth();
        return new Date(year, month, 1);
    }

    public static Date getFirstDateOfMonth(int year, int month) {
        year = year - 1900;
        month = month - 1;
        return new Date(year, month, 1);
    }

    public static Date getFirstDateOfNextMonth() {
        Date current = new Date();
        int year = current.getYear();
        int month = current.getMonth();
        return new Date(year, month + 1, 1);
    }

    public static Date getFirstDateOfNextMonth(int year, int month) {
        year = year - 1900;
        month = month - 1;
        return new Date(year, month + 1, 1);
    }

    public static int getTotalDaysForOneMonth(int year, int month) {
        if (year % 4 == 0 && month == 2) {
            return 29;
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                return 31;
            } else if (month == 2) {
                return 28;
            }
        }
        return 30;
    }

    public static void main(String[] args) {
        System.out.println(getFirstDateOfMonth());
        System.out.println(getFirstDateOfNextMonth());
        System.out.println(getFirstDateOfMonth(2014, 05));
        System.out.println(getFirstDateOfNextMonth(2014, 05));
    }
}
