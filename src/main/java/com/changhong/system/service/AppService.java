package com.changhong.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.system.domain.AppChangeHistory;
import com.changhong.system.domain.BoxRecommend;
import com.changhong.system.domain.BoxRecommendRecord;
import com.changhong.system.domain.LuncherRecommend;
import com.changhong.system.web.facade.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:30
 */
public interface AppService {

    /**************************************类别部分****************************************/

    List<AppCategoryDTO> obtainAllFirstLevelCategory(boolean includeChildren);

    void saveOrUpdateAppCategory(int categoryId, String categoryName, int parentId, MultipartFile iconFile);

    AppCategoryDTO obtainAppCategoryById(int categoryId);

    void deleteAppCategory(int categoryId);

    boolean obtainCategoryHasApps(int categoryId);

    /**************************************专题部分****************************************/

    List<AppTopicDTO> obtainAllTopics();

    void saveOrUpdateAppTopic(int topicId, String topicName, MultipartFile iconFile);

    AppTopicDTO obtainAppTopicById(int topicId);

    void deleteAppTopic(int topicId);

    /**************************************应用部分****************************************/

    List<MarketAppDTO> obtainMarketApps(String appName, int categoryId, int topicId, String appStatus, int startPosition, int pageSize);

    int obtainMarketAppSize(String appName, int categoryId, int topicId, String appStatus);

    MarketAppDTO obtainMarketAppById(int marketAppId);

    int saveOrUpdateMarketApp(MarketAppDTO appDTO) throws Exception;

    void changeMarketAppStatus(int marketAppId, String resetStatus);

    JSONArray obtainPageAppsByStartNumber(int startNumber);

    List<AppChangeHistoryDTO> obtainAppChangeHistories(int appId);

    /**************************************推荐部分****************************************/

    BoxRecommendRecord obtainBoxRecommendRecord();

    List<BoxRecommendDTO> obtainAllBoxRecommends();

    JSONArray obtainRecommendApps(int appId, int categoryId, String appName);

    boolean updateBoxRecommendPosition(int pageNumber, int recommendPosition, int marketAppId, int currentUserId);

    void updateBoxRecommend();

    void cancelBoxRecommend();

    JSONObject obtainCheckIsAppRecommend(int appId, int pageNumber, int recommendPosition);

    /************************************LUNCHER推荐部分************************************/

    List<LuncherRecommendDTO> obtainAllLuncherRecommend();

    String updateLuncherRecommendPosition(int appId);

    void deleteLuncherRecommendPosition(int recommendId);

    String obtainCheckIsLauncherAppRecommend(int appId);

    /************************************应用强制升级和卸载************************************/

    List<AppMustDTO> obtainAppMust(boolean install);

    String updateAppMust(int appId, boolean install);

    void deleteAppMust(int appMustId);
}
