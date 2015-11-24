package com.changhong.common.utils;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * User: Peng Jie
 */
public class CHMemcacheUtils {

    private final static Logger log = LogManager.getLogger(CHMemcacheUtils.class);

    private int localCacheTime = 10 * 60;

    private final static String CACHE_NAME = "mclient0";

    private ICacheManager<IMemcachedCache> manager;

    private IMemcachedCache cacheClient;


    public boolean initMemCache() {
        log.info("Memcache.int() start");
        try {
            manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
            manager.setConfigFile("memcached.xml");
            manager.setResponseStatInterval(5 * 1000);
            manager.start();

            //every time clear before init
            cacheClient = manager.getCache(CACHE_NAME);
        } catch (Exception e) {
            log.error(e);
            return false;
        }

        log.info("Memcache.int() end");
        return true;
    }

    public boolean put(String key, Object value) {
        try {
            cacheClient.put(key, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Object get(String key) {
        return cacheClient.get(key, localCacheTime);
    }

    public Object getImmediate(String key) {
        return cacheClient.get(key);
    }

    public boolean containsKey(String key) {
        return cacheClient.containsKey(key);
    }

    public Set<String> keySet() {
        return cacheClient.keySet();
    }

    public Object remove(String key) {
        return cacheClient.remove(key);
    }

    public void clear() {
        cacheClient.clear();
    }

    public void stop() {
        manager.stop();
    }

    public static void main(String[] args) {
        CHMemcacheUtils memcacheUtils = new CHMemcacheUtils();
        memcacheUtils.initMemCache();
    }
}
