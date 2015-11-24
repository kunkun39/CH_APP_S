package com.changhong.client.service;

import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.system.web.facade.dto.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

    @Value("${application.localcache}")
    private boolean localCache;

    private CacheService cacheService;

    public void obtainInitCachedObjects() {
        log.info("local cache : " + localCache);
        if (localCache) {
            cacheService = (CacheService) ApplicationEventPublisher.getCtx().getBean("localcacheService");
        } else {
            cacheService = (CacheService) ApplicationEventPublisher.getCtx().getBean("memcacheService");
        }
        cacheService.obtainInitCachedObjects();
    }

    public void processDestoryCached() {
        cacheService.processDestoryCached();
    }

    /************************************文件服务器************************************/

    public void resetMultipHost(int hostId, String hostName, boolean remove) {
        cacheService.resetMultipHost(hostId, hostName, remove);
    }

    public String getRandomMutipHost() {
        return cacheService.getRandomMutipHost();
    }

    /************************************专题和类别部分************************************/

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

    /************************************App部分************************************/

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

    /************************************LUNCHER推荐部分************************************/

    public void resetLuncherRecommendInCache(LuncherRecommendDTO dto, boolean remove) {
        cacheService.resetLuncherRecommendInCache(dto, remove);
    }

    public List<LuncherRecommendDTO> obtainLuncherRecommends() {
        return cacheService.obtainLuncherRecommends();
    }

    /************************************应用强制升级和卸载************************************/

    public void resetAppMustInCache(AppMustDTO dto, boolean remove) {
        cacheService.resetAppMustInCache(dto, remove);
    }

    public List<AppMustDTO> obtainAppMust() {
        return cacheService.obtainAppMust();
    }

    /************************************开机图片************************************/

    public String getBootImageFileName() {
        return cacheService.getBootImageFileName();
    }

    public void setBootImageFileName(String bootImageFileName) {
        cacheService.setBootImageFileName(bootImageFileName);
    }

    /************************************系统版本************************************/

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
