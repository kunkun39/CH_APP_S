package com.changhong.client.domain;

/**
 * User: Jack Wang
 * Date: 15-11-10
 * Time: 上午10:31
 */
public class AppFast implements Comparable<AppFast>{

    private int appId;

    private int count;

    public AppFast(int appId) {
        this.appId = appId;
        this.count = 0;
    }

    public void add() {
        this.count++;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int compareTo(AppFast o) {
        return this.count <= o.getCount() ? 1 : -1;
    }
}
