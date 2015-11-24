package com.changhong.client.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * User: :Peng Jie
 */
public class SyncMemCacheTask {

    private final static Logger log = LogManager.getLogger(SyncMemCacheTask.class);

    private CHCallBack chCallBack;

    public void syncMemCache() {
        if (chCallBack != null) {
            chCallBack.onCallBack();
        } else {
            log.info("syn memcache job error!");
        }
    }

    public void setChCallBack(CHCallBack chCallBack) {
        this.chCallBack = chCallBack;
    }
}
