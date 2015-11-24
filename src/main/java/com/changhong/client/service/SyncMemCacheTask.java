package com.changhong.client.service;

import com.changhong.common.utils.CHCallBack;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-11-24
 * Time: 上午9:08
 * To change this template use File | Settings | File Templates.
 */
public class SyncMemCacheTask {

    private final static Logger log = LogManager.getLogger(SyncMemCacheTask.class);

    public void setChCallBack(CHCallBack chCallBack) {
        this.chCallBack = chCallBack;
    }

    private CHCallBack chCallBack;

    public void syncMemCache() {
        if(chCallBack != null) {
            chCallBack.onCallBack();
        }
        else {
            log.info("error!");
        }
    }
}
