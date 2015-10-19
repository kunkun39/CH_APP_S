package com.changhong.system.domain;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:41
 */
public class ClientVersion {

    public final static String APK_FILE_NAME = "GDAppStore.apk";

    private int id;

    private int clientVersion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }
}
