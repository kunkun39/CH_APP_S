package com.changhong.client.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * User: :Peng Jie
 */
public class SyncMemCacheTask {

    private final static Logger log = LogManager.getLogger(SyncMemCacheTask.class);

    private SyncCallBack syncCallBack;

    public void setSyncCallBack(SyncCallBack syncCallBack) {
        this.syncCallBack = syncCallBack;
    }

    public void syncMemCache() {
        if (syncCallBack != null) {
            syncCallBack.sync();
        } else {
            log.info("syn memcache job error!");
        }
    }
}
