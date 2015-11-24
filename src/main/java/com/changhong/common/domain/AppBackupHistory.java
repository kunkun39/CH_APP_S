package com.changhong.common.domain;

import org.joda.time.DateTime;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-11-10
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class AppBackupHistory {

    public final static int BACKUP = 0;

    public final static int DELETTE = 1;

    public final static int RECOVER = 2;

    private int year;

    private int month;

    private int day;

    private int hour;

    private int opcode; // 0：备份；1：删除；2：恢复

    private String boxMac;

    private String appIds;

    public AppBackupHistory() {

    }

    public AppBackupHistory(String boxMac, String appIds, int opcode) {
        this.boxMac = boxMac;
        this.appIds = appIds;
        this.opcode = opcode;
        DateTime time = new DateTime();
        this.year = time.getYear();
        this.month = time.getMonthOfYear();
        this.day = time.getDayOfMonth();
        this.hour = time.getHourOfDay();
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

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public String getBoxMac() {
        return boxMac;
    }

    public void setBoxMac(String boxMac) {
        this.boxMac = boxMac;
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }
}
