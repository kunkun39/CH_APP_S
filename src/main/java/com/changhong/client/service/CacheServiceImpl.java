package com.changhong.client.service;

import com.changhong.system.web.facade.dto.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午11:17
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private final static Logger log = LogManager.getLogger(CacheServiceImpl.class);

    @Autowired
    CacheService localcacheService;
    @Autowired
    CacheService memcacheService;

    private boolean localcache;

    private CacheService cacheService;

    @Value("${application.localcache}")
    public void setLocalcache(boolean localcache) {
        this.localcache = localcache;
    }

    public void obtainInitCachedObjects() {
        log.info("localcache : " + localcache);
        if (localcache) {
            cacheService = localcacheService;
        }
        else {
            cacheService = memcacheService;
        }
        cacheService.obtainInitCachedObjects();
    }

    public void processDestoryCached() {
        cacheService.processDestoryCached();
    }

    public void resetAppCategoryInCache(AppCategoryDTO dto, boolean remove) {
        cacheService.resetAppCategoryInCache(dto, remove);
    }

    public Collection<AppCategoryDTO> obtainAllCategories() {
        return cacheService.obtainAllCategories();
    }

    public void resetAppTopicInCache(AppTopicDTO dto, boolean remove) {
        cacheService.resetAppTopicInCache(dto, remove);
    }

    public Collection<AppTopicDTO> obtainAllTopics() {
        return cacheService.obtainAllTopics();
    }

    public void resetMarketAppInCache(MarketAppDTO dto) {
        cacheService.resetMarketAppInCache(dto);
    }

    public MarketAppDTO obtainMarketAppInCache(int appId) {
        return cacheService.obtainMarketAppInCache(appId);
    }

    public List<MarketAppDTO> obtainMarketAppInCache(String[] appPackages) {
        return cacheService.obtainMarketAppInCache(appPackages);
    }

    public List<MarketAppDTO> obtainCachedAppByCategoryId(int categoryId) {
        return cacheService.obtainCachedAppByCategoryId(categoryId);
    }

    public List<MarketAppDTO> obtainCachedAppByTopicId(int topicId) {
        return cacheService.obtainCachedAppByTopicId(topicId);
    }

    public List<MarketAppDTO> obtainSearchApps(String keyWords) {
        return cacheService.obtainSearchApps(keyWords);
    }

    public int obtainAppCategoryId(int appId) {
        return cacheService.obtainAppCategoryId(appId);
    }

    public int obtainCategoryFatherId(int appId) {
        return cacheService.obtainCategoryFatherId(appId);
    }

    public void updateAppDownloadTimes(int appId) {
        cacheService.updateAppDownloadTimes(appId);
    }

    public void resetLuncherRecommendInCache(LuncherRecommendDTO dto, boolean remove) {
        cacheService.resetLuncherRecommendInCache(dto, remove);
    }

    public List<LuncherRecommendDTO> obtainLuncherRecommends() {
        return cacheService.obtainLuncherRecommends();
    }

    public void resetAppMustInCache(AppMustDTO dto, boolean remove) {
        cacheService.resetAppMustInCache(dto, remove);
    }

    public List<AppMustDTO> obtainAppMust() {
        return cacheService.obtainAppMust();
    }

    public String getBootImageFileName() {
        return cacheService.getBootImageFileName();
    }

    public void setBootImageFileName(String bootImageFileName) {
        cacheService.setBootImageFileName(bootImageFileName);
    }

    public int getCurrentClientVersion() {
        return cacheService.getCurrentClientVersion();
    }

    public void setCurrentClientVersion(int clientVersion) {
        cacheService.setCurrentClientVersion(clientVersion);
    }

    public boolean isClientBeginUpdate() {
        return cacheService.isClientBeginUpdate();
    }

    public void setClientBeginUpdate(boolean clientBeginUpdate) {
        cacheService.setClientBeginUpdate(clientBeginUpdate);
    }
}
