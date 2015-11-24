package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-11-23
 * Time: 上午10:15
 */
public class MultipHost extends EntityBase {

    private String hostName;

    private boolean hostEnabled;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isHostEnabled() {
        return hostEnabled;
    }

    public void setHostEnabled(boolean hostEnabled) {
        this.hostEnabled = hostEnabled;
    }
}
