package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 15-9-25
 * Time: 下午3:03
 */
public class LuncherRecommendDTO implements Serializable {

    private int id;

    private int position;

    private int appId;

    private String appName;

    private String appVersion;

    private String appKey;

    private String iconActualFileName;

    public LuncherRecommendDTO() {
    }

    public LuncherRecommendDTO(int id, int position, int appId, String appName, String appVersion,  String appKey, String iconActualFileName) {
        this.id = id;
        this.position = position;
        this.appId = appId;
        this.appName = appName;
        this.appVersion = appVersion;
        this.appKey = appKey;
        this.iconActualFileName = iconActualFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getIconActualFileName() {
        return iconActualFileName;
    }

    public void setIconActualFileName(String iconActualFileName) {
        this.iconActualFileName = iconActualFileName;
    }
}
