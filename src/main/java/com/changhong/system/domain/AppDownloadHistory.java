package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午1:27
 */
public class AppDownloadHistory extends EntityBase {

    private String boxMac;

    private int appId;

    private int appfatherCategoryId;

    private int appCategoryId;

    private int year;

    private int month;

    private int day;

    private int hour;

    public AppDownloadHistory() {
    }

    public AppDownloadHistory(String boxMac, int appId, int appfatherCategoryId, int appCategoryId) {
        this.boxMac = boxMac;
        this.appId = appId;
        this.appfatherCategoryId = appfatherCategoryId;
        this.appCategoryId = appCategoryId;
        DateTime time = new DateTime();
        this.year = time.getYear();
        this.month = time.getMonthOfYear();
        this.day = time.getDayOfMonth();
        this.hour = time.getHourOfDay();
    }

    public AppDownloadHistory(String boxMac, int appId, int appfatherCategoryId, int appCategoryId, int year, int month, int day, int hour) {
        this.boxMac = boxMac;
        this.appId = appId;
        this.appfatherCategoryId = appfatherCategoryId;
        this.appCategoryId = appCategoryId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    /********************************************GET/SET***********************************************/

    public String getBoxMac() {
        return boxMac;
    }

    public void setBoxMac(String boxMac) {
        this.boxMac = boxMac;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAppfatherCategoryId() {
        return appfatherCategoryId;
    }

    public void setAppfatherCategoryId(int appfatherCategoryId) {
        this.appfatherCategoryId = appfatherCategoryId;
    }

    public int getAppCategoryId() {
        return appCategoryId;
    }

    public void setAppCategoryId(int appCategoryId) {
        this.appCategoryId = appCategoryId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
