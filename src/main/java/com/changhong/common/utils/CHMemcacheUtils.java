package com.changhong.common.utils;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
import com.changhong.client.service.LocalCacheServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-11-19
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public class CHMemcacheUtils {

    private final static Logger log = LogManager.getLogger(CHMemcacheUtils.class);

    private int localCacheTime = 10 * 60;

    private final static String CACHE_NAME = "mclient0";

    private ICacheManager<IMemcachedCache> manager;

    private IMemcachedCache memcachedCache;

    private CHCallBack callBack;

    public CHMemcacheUtils(CHCallBack callBack) {
        this.callBack = callBack;
    }

    public boolean initMemCache() {
        log.info("Memcache.int() start");
        try {
            manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
            manager.setConfigFile("memcached.xml");
            manager.setResponseStatInterval(5*1000);
            manager.start();

            memcachedCache = manager.getCache(CACHE_NAME);
        }catch(Exception e) {
            log.error(e);
            return false;
        }
        if(callBack != null) {
            callBack.onCallBack();
        }
        log.info("Memcache.int() end");
        return true;
    }

    public void setLocalCacheTime(int localCacheTime) {
        if (localCacheTime > 0) {
            this.localCacheTime = localCacheTime;
        }
    }

    public boolean put(String key, Object value) {
        try {
            memcachedCache.put(key, value);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public Object get(String key) {
        return memcachedCache.get(key, localCacheTime);
    }

    public Object getImmediate(String key) {
        return memcachedCache.get(key);
    }

    public boolean containsKey(String key) {
        return memcachedCache.containsKey(key);
    }

    public Set<String> keySet() {
        return memcachedCache.keySet();
    }

    public Object remove(String key) {
        return memcachedCache.remove(key);
    }

    public void stop() {
        manager.stop();
    }
    public static void main(String[] args) {
        CHMemcacheUtils memcacheUtils = new CHMemcacheUtils(null);
        memcacheUtils.initMemCache();
    }
}
