package com.changhong.client.service;

import com.changhong.system.web.facade.dto.*;

import java.util.Collection;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午11:17
 */
public interface CacheService {

    void obtainInitCachedObjects();

    void processDestoryCached();
    /************************************专题和类别部分************************************/

    /**
     * 1 - 种类删除需要清空缓存
     * 2 - 种类信息改变需要修改缓存
     */
    void resetAppCategoryInCache(AppCategoryDTO dto, boolean remove);

    Collection<AppCategoryDTO> obtainAllCategories();

    /**
     * 1 - 专题删除需要清空缓存
     * 2 - 专题信息改变需要修改缓存
     */
    void resetAppTopicInCache(AppTopicDTO dto, boolean remove);

    Collection<AppTopicDTO> obtainAllTopics();

    /************************************App部分************************************/

    /**
     * 1 - 应用信息改变需要修改缓存
     * 2 - 应用状态不是通过，需要清空缓存
     */
    void resetMarketAppInCache(MarketAppDTO dto);

    MarketAppDTO obtainMarketAppInCache(int appId);

    List<MarketAppDTO> obtainMarketAppInCache(String[] appPackages);

    List<MarketAppDTO> obtainCachedAppByCategoryId(int categoryId);

    List<MarketAppDTO> obtainCachedAppByTopicId(int topicId);

    List<MarketAppDTO> obtainSearchApps(String keyWords);

    int obtainAppCategoryId(int appId);

    int obtainCategoryFatherId(int appId);

    void updateAppDownloadTimes(int appId);

    /************************************LUNCHER推荐部分************************************/

    /**
     * 1 - 推荐信息改变需要修改缓存
     * 2 - 推荐删除
     */
    void resetLuncherRecommendInCache(LuncherRecommendDTO dto, boolean remove);

    List<LuncherRecommendDTO> obtainLuncherRecommends();

    /************************************应用强制升级和卸载************************************/

    /**
     * 1 - 推荐信息改变需要修改缓存
     * 2 - 推荐删除
     */
    void resetAppMustInCache(AppMustDTO dto, boolean remove);

    List<AppMustDTO> obtainAppMust();

    /************************************开机图片************************************/

    String getBootImageFileName();

    void setBootImageFileName(String bootImageFileName);

    /************************************系统版本************************************/

    int getCurrentClientVersion();

    void setCurrentClientVersion(int clientVersion);

    boolean isClientBeginUpdate();

    void setClientBeginUpdate(boolean clientBeginUpdate);
}
