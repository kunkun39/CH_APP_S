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

    private static ICacheManager<IMemcachedCache> manager;

    private static IMemcachedCache currentCache;

    private static String currentCacheName;

    public static boolean initMemCache() {
        log.info("Memcache.int() start");
        try {
            manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
            manager.setConfigFile("memcached.xml");
            manager.setResponseStatInterval(3 * 1000);
            manager.start();

            currentCacheName = "mclient0";
            currentCache = cache0();
        } catch (Exception e) {
            log.error(e);
            return false;
        }

        log.info("Memcache.int() end");
        return true;
    }

    public static IMemcachedCache cache0(){
        return manager.getCache("mclient0");
    }

    public static IMemcachedCache cache1(){
        return manager.getCache("mclient1");
    }

    /**
     * get, gets, put, remove first get value from one client which decide by key hash code
     * after handle successful then sync to other clients
     */
    public static Object get(String key) {
        return currentCache.get(key);
    }

    public static Object[] gets(String[] keys) {
        Object[] objects = currentCache.getMultiArray(keys);
        if (objects != null) {
            for (Object object : objects) {
                if (object == null) {
                    clusterCopy();
                    objects = currentCache.getMultiArray(keys);
                    break;
                }
            }
        }
        return objects;
    }

    public static boolean put(String key, Object value) {
        try {
            currentCache.put(key, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Object remove(String key) {
        return currentCache.remove(key);
    }

    /**
     * get all ket from current helper client, this action not check again if this keyset is null
     */
    public static Set<String> keySet() {
        Set<String> set = currentCache.keySet();
        if (set == null || set.isEmpty()) {
            if ("mclient0".equals(currentCacheName)) {
                currentCacheName = "mclient1";
                currentCache = cache1();
                log.error("switch memcache to client1");
            } else {
                currentCacheName = "mclient0";
                currentCache = cache0();
                log.error("switch memcache to client0");
            }
            set = currentCache.keySet();
        }
        return set;
    }

    public static void clusterCopy() {
        if ("mclient0".equals(currentCacheName)) {
             manager.clusterCopy("mclient0", "cluster1");
            log.error("copy memcache to client1");
        } else {
            manager.clusterCopy("mclient1", "cluster1");
            log.error("copy memcache to client0");
        }
    }

    public static void clear() {
        currentCache.clear();
    }

    public static void stop() {
        manager.stop();
    }

    public static void main(String[] args) {
        initMemCache();

        Set<String> set = cache1().keySet();

//        cache0().clear();
//        cache1().clear();
//
//        cache0().put("key001", "value00001");
//        cache0().put("key002", "value00002");
//
//        String values = (String)cache0().get("key002");
//        System.out.println("get key:key0002=" + values);
//        values = (String)cache1().get("key002");
//        System.out.println("get key:key0002=" + values);
//
//        User user = new User("foejfoe", "fjeojfeo", "kunkun39", "jack80874042");
//        cache0().put("USER_" + user.getId(), user);
//
//        User getUser = (User)cache1().get("USER_" + user.getId());
//        System.out.println("get key:USER_" + getUser.getId() + " username:" + getUser.getUsername());

        stop();
    }
}
