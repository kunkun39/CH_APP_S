package com.changhong.client.service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:15
 */
public interface ClientService {

    /**
     * 客户端获得分类信息
     */
    String obtainAllAppCategoryInfo();

    /**
     * 客户端获得所有页面的展示效果
     */
    String obtainBoxIndexPageInfo();

    /**
     * 客户端获得所有分类APP
     */
    String obtainCategoryApps(int categoryId);

    /**
     * 客户端获得APP的详情
     */
    String obtainAppDetailsInfo(int appId);

    /**
     * 获取APP详情中的推荐，按照分类中最流行的
     */
    String obtainAppDetailsRecommend(int categoryId);

    /**
     * 搜索应用
     */
    String obtainSearchApps(String keyWords);

    /**
     * 获得排行榜APPS
     */
    String obtainRankListApps();

    /**
     * 获得应用用户
     */
    String obtainAppsVersions(String[] appPackages);

    /**
     * 下载应用
     */
    void updateAppDownloadTime(int appId, String boxMac);

    /**
     * 获得luncher推荐
     */
    String obtainLuncherAppRecommends();

    /**
     * 强制
     */
    String obtainAppMusts();
}
