package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 15-9-25
 * Time: 下午3:03
 */
public class AppMustDTO implements Serializable {

    private int id;

    private boolean install;

    private int appId;

    private String appName;

    private String appVersion;

    private int appVersionInt;

    private String appKey;

    private String iconActualFileName;

    private String packageName;

    private String apkActualFileName;

    public AppMustDTO() {
    }

    public AppMustDTO(int id, boolean install, int appId, String appName, String appVersion, int appVersionInt, String appKey, String iconActualFileName,
                      String packageName, String apkActualFileName) {
        this.id = id;
        this.install = install;
        this.appId = appId;
        this.appName = appName;
        this.appVersion = appVersion;
        this.appVersionInt = appVersionInt;
        this.appKey = appKey;
        this.iconActualFileName = iconActualFileName;
        this.packageName = packageName;
        this.apkActualFileName = apkActualFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInstall() {
        return install;
    }

    public void setInstall(boolean install) {
        this.install = install;
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

    public int getAppVersionInt() {
        return appVersionInt;
    }

    public void setAppVersionInt(int appVersionInt) {
        this.appVersionInt = appVersionInt;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkActualFileName() {
        return apkActualFileName;
    }

    public void setApkActualFileName(String apkActualFileName) {
        this.apkActualFileName = apkActualFileName;
    }
}
