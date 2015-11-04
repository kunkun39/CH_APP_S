package com.changhong.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.service.CacheService;
import com.changhong.common.domain.EntityBase;
import com.changhong.common.exception.CHApplicationException;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.*;
import com.changhong.system.repository.AppDao;
import com.changhong.system.web.facade.assember.*;
import com.changhong.system.web.facade.dto.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:30
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    private static final Log logger = LogFactory.getLog(AppServiceImpl.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AppDao appDao;

    /**************************************类别部分****************************************/

    public List<AppCategoryDTO> obtainAllFirstLevelCategory(boolean includeChildren) {
        List<AppCategory> categories = appDao.loadAllFirstLevelCategory();
        return AppCategoryWebAssember.toAppCategoryDTOList(categories, includeChildren);
    }

    public void saveOrUpdateAppCategory(int categoryId, String categoryName, int parentId, MultipartFile iconFile) {
        AppCategory category = AppCategoryWebAssember.toAppCategoryDomain(categoryId, categoryName, parentId);

        if (parentId > 0) {
            CategoryIcon icon = null;
            if (iconFile != null && iconFile.getSize() > 0) {
                icon = category.getCategoryIcon();
                if (icon != null && categoryId > 0) {
                    icon.setFile(iconFile);
                } else {
                    icon = new CategoryIcon(iconFile, "");
                }
                documentService.uploadCategoryIconData(icon);
                appDao.saveOrUpdate(icon);
            }
            if (icon != null) {
                category.setCategoryIcon(icon);
            }
        }

        //reset the cache
        AppCategoryDTO dto = AppCategoryWebAssember.toAppCategoryDTO(category, false);
        cacheService.resetAppCategoryInCache(dto, false);

        appDao.saveOrUpdate(category);
    }

    public AppCategoryDTO obtainAppCategoryById(int categoryId) {
        AppCategory category = (AppCategory) appDao.findById(categoryId, AppCategory.class);
        AppCategoryDTO dto = AppCategoryWebAssember.toAppCategoryDTO(category, false);
        return dto;
    }

    public void deleteAppCategory(int categoryId) {
        AppCategory category = (AppCategory) appDao.findById(categoryId, AppCategory.class);
        appDao.delete(category);

        //delete file
        if (category.getCategoryIcon() != null) {
            documentService.deleteCategoryIconData(category.getCategoryIcon().getActualFileName());
        }

        //reset the cache
        AppCategoryDTO dto = new AppCategoryDTO();
        dto.setId(categoryId);
        cacheService.resetAppCategoryInCache(dto, true);
    }

    public boolean obtainCategoryHasApps(int categoryId) {
        int size = appDao.loadCategoryApps(categoryId);
        return size > 0 ? true : false;
    }

    /**************************************应用部分****************************************/

    public List<MarketAppDTO> obtainMarketApps(String appName, int categoryId, String appStatus, int startPosition, int pageSize) {
        List<MarketApp> apps = appDao.loadMarketApps(appName, categoryId, appStatus, startPosition, pageSize);
        return MarketAppWebAssember.toMarketAppDTOList(apps);
    }

    public int obtainMarketAppSize(String appName, int categoryId, String appStatus) {
        return appDao.loadMarketAppSize(appName, categoryId, appStatus);
    }

    public MarketAppDTO obtainMarketAppById(int marketAppId) {
        MarketApp app = (MarketApp) appDao.findById(marketAppId, MarketApp.class);
        return MarketAppWebAssember.toMarketAppDTO(app);
    }

    public boolean obtainAppPackageDuplicate(int marketAppId, String packageName) {
        return appDao.loadAppPackageDuplicate(marketAppId, packageName);
    }

    public int saveOrUpdateMarketApp(MarketAppDTO appDTO) {
        MarketApp app = MarketAppWebAssember.toMarkAppDomain(appDTO);

        //save icon
        MultipartFile iconFile = appDTO.getIconFile();
        if(iconFile != null && iconFile.getSize() > 0) {
            if (app.getAppIcon() != null) {
                AppIcon icon = app.getAppIcon();
                icon.changeFile(iconFile);
            } else {
                AppIcon icon = new AppIcon(iconFile, "");
                app.setAppIcon(icon);
            }
            documentService.uploadAppIconData(app);
        }

        //save apk
        MultipartFile apkFile = appDTO.getApkFile();
        if(apkFile != null && apkFile.getSize() > 0) {
            AppFile apk = null;
            if (app.getAppFile() != null) {
                apk = app.getAppFile();
                apk.changeFile(apkFile);
            } else {
                apk = new AppFile(apkFile, "");
                app.setAppFile(apk);
            }

            documentService.uploadAppApkData(app);
            app.setAppSize(String.valueOf(apkFile.getSize()));
        }

        //save poster
        MultipartFile posterFile = appDTO.getPosterFile();
        if(posterFile != null && posterFile.getSize() > 0) {
            AppPoster poster = null;
            if (app.getAppPoster() != null) {
                poster = app.getAppPoster();
                poster.changeFile(posterFile);
            } else {
                poster = new AppPoster(posterFile, "");
                app.setAppPoster(poster);
            }

            documentService.uploadAppPosterData(app);
        }

        //save domain
        app.resetUpdateTime();
        app.resetPingYin();
        appDao.saveOrUpdate(app);

        //save history
        AppChangeHistory history = app.getHistory();
        if (history != null && history.getFieldDetails() != null && !history.getFieldDetails().isEmpty()) {
            User currentUser = new User();
            currentUser.setId(SecurityUtils.currectUserId());
            history.setUser(currentUser);
            history.setApp(app);
            appDao.saveOrUpdate(history);
        }

        //reset the cache
        MarketAppDTO afterSave = MarketAppWebAssember.toMarketAppDTO(app);
        cacheService.resetMarketAppInCache(afterSave);

        return app.getId();
    }

    public void changeMarketAppStatus(int marketAppId, String resetStatus) {
        MarketApp app = (MarketApp) appDao.findById(marketAppId, MarketApp.class);
        AppChangeHistory history = DomainHelper.generateStatusChangeHistory(app, app.getAppStatus().name(), resetStatus);
        app.setAppStatus(AppStatus.valueOf(resetStatus));

        //reset the cache
        MarketAppDTO afterSave = MarketAppWebAssember.toMarketAppDTO(app);
        cacheService.resetMarketAppInCache(afterSave);

        //record change history
        appDao.saveOrUpdate(history);
    }

    public JSONArray obtainPageAppsByStartNumber(int startNumber) {
        List<MarketApp> apps = appDao.loadPageAppsByStartNumber(startNumber);
        JSONArray array = new JSONArray();

        if (apps != null) {
            for (MarketApp app : apps) {
                JSONObject o = new JSONObject();
                o.put("appFullName", app.getAppName());
                o.put("appVersion", app.getAppVersion());
                o.put("appKey", app.getAppKey());
                o.put("downloadTimes", app.getDownloadTimes());
                o.put("iconActualFileName", app.getAppIcon().getActualFileName());

                array.add(o);
            }
        }

        return array;
    }

    public List<BoxRecommendDTO> obtainAllBoxRecommends() {
        List<BoxRecommend> recommends = appDao.loadAllBoxRecommends();
        return BoxRecommendWebAssember.toBoxRecommendDTOList(recommends);
    }

    public List<AppChangeHistoryDTO> obtainAppChangeHistories(int appId) {
        List<AppChangeHistory> histories = appDao.loadAppChangeHistories(appId);
        return AppChangeHistoryAssember.toAppChangeHistoryDTOList(histories);
    }

    /**************************************推荐部分****************************************/

    public BoxRecommendRecord obtainBoxRecommendRecord() {
        BoxRecommendRecord record = (BoxRecommendRecord) appDao.findById(1, BoxRecommendRecord.class);
        if (record == null) {
            throw new CHApplicationException("have not init box recommend record yet");
        }
        return record;
    }

    public JSONArray obtainRecommendApps(int appId, int categoryId, String appName) {
        List<MarketApp> apps = new ArrayList<MarketApp>();

        if (appId > 0) {
            apps.add((MarketApp) appDao.findById(appId, MarketApp.class));
        }  else {
            apps = appDao.loadRecommendApps(categoryId, appName);
        }

        JSONArray array = new JSONArray();
        if (apps != null) {
            for (MarketApp app : apps) {
                JSONObject o = new JSONObject();
                o.put("appId", app.getId());
                o.put("appFullName", app.getAppName());
                o.put("appVersion", app.getAppVersion());
                o.put("appKey", app.getAppKey());
                o.put("appDescription", app.getAppDescription());
                o.put("appCategory", app.getAppCategory().getFullCategoryPath());
                o.put("iconActualFileName", app.getAppIcon().getActualFileName());
                o.put("posterActualFileName", app.getAppPoster().getActualFileName());

                array.add(o);
            }
        }

        return array;
    }

    public boolean updateBoxRecommendPosition(int pageNumber, int recommendPosition, int marketAppId, int currentUserId) {
        //check if current user change
        User currentUser = (User) appDao.findById(currentUserId, User.class);
        BoxRecommendRecord record = (BoxRecommendRecord) appDao.findById(1, BoxRecommendRecord.class);

        //not
        if (record.getChangeUserId() != currentUser.getId()) {
            //check if somebody change
            if (record.getChangeUserId() != -1) {
                return false;
            }
        }

        //yes
        BoxRecommend recommend = appDao.loadBoxRecommendPosition(pageNumber, recommendPosition);
        if (recommend == null) {
            recommend = new BoxRecommend(pageNumber, recommendPosition);
        }
        MarketApp app = new MarketApp();
        app.setId(marketAppId);

        recommend.setTmpMarketApp(app);
        appDao.saveOrUpdate(recommend);

        record.addChangeLock(currentUser.getId(), currentUser.getName());
        appDao.saveOrUpdate(record);

        return true;
    }

    public void updateBoxRecommend() {
        List<BoxRecommend> recommends = appDao.loadAllBoxRecommends();
        for (BoxRecommend recommend : recommends) {
            MarketApp tmpMarketApp = recommend.getTmpMarketApp();

            if (tmpMarketApp != null) {
                recommend.setMarketApp(tmpMarketApp);
                recommend.setTmpMarketApp(null);
            }
        }
        appDao.saveAll(recommends);

        BoxRecommendRecord record = (BoxRecommendRecord) appDao.findById(1, BoxRecommendRecord.class);
        record.releaseChangeLock();
        appDao.saveOrUpdate(record);
    }

    public void cancelBoxRecommend() {
        List<BoxRecommend> recommends = appDao.loadAllBoxRecommends();
        for (BoxRecommend recommend : recommends) {
            recommend.setTmpMarketApp(null);
        }
        appDao.saveAll(recommends);

        BoxRecommendRecord record = (BoxRecommendRecord) appDao.findById(1, BoxRecommendRecord.class);
        record.releaseChangeLock();
        appDao.saveOrUpdate(record);
    }

    public JSONObject obtainCheckIsAppRecommend(int appId, int pageNumber, int recommendPosition) {
        BoxRecommend recommend = appDao.loadCheckIsAppRecommend(appId, pageNumber, recommendPosition);

        JSONObject o = new JSONObject();
        if (recommend != null) {
            o.put("page_index", recommend.getPageNumber());
            o.put("position_index", recommend.getRecommendPosition());
        } else {
            o.put("page_index", -1);
            o.put("position_index", -1);
        }
        return o;
    }

    /************************************LUNCHER推荐部分************************************/

    public List<LuncherRecommendDTO> obtainAllLuncherRecommend() {
        List<LuncherRecommend> recommends = appDao.loadAllLuncherRecommend();
        return LuncherRecommendWebAssember.toLuncherRecommendDTOList(recommends);
    }

    public String updateLuncherRecommendPosition(int appId) {
        try {
            MarketApp app = (MarketApp) appDao.findById(appId, MarketApp.class);

            int position = appDao.loadMaxLuncherRecommendPosition();
            LuncherRecommend recommend = new LuncherRecommend(position, app);
            appDao.saveOrUpdate(recommend);

            //reset the cache
            LuncherRecommendDTO dto = LuncherRecommendWebAssember.toLuncherRecommendDTO(recommend);
            cacheService.resetLuncherRecommendInCache(dto, false);

            JSONObject o = new JSONObject();
            o.put("recommendId", recommend.getId());
            o.put("position", recommend.getPosition());

            return o.toJSONString();
        } catch (Exception e) {
            logger.error("save luncher box failed", e);
            return null;
        }
    }

    public void deleteLuncherRecommendPosition(int recommendId) {
        LuncherRecommend recommend = (LuncherRecommend) appDao.findById(recommendId, LuncherRecommend.class);

        //reset the cache
        LuncherRecommendDTO dto = LuncherRecommendWebAssember.toLuncherRecommendDTO(recommend);
        cacheService.resetLuncherRecommendInCache(dto, true);

        int position = recommend.getPosition();
        appDao.delete(recommend);

        List<LuncherRecommend> recommends = appDao.loadAllLuncherRecommend();
        for (LuncherRecommend luncherRecommend : recommends) {
            if(luncherRecommend.getPosition() > position)
            luncherRecommend.setPosition(luncherRecommend.getPosition() - 1);
        }
    }

    /************************************应用强制升级和卸载************************************/

    public List<AppMustDTO> obtainAppMust(boolean install) {
        List<AppMust> appMusts = appDao.loadAppMust(install);
        return AppMustWebAssember.toAppMustDTOList(appMusts);
    }

    public String updateAppMust(int appId, boolean install) {
        try {
            MarketApp app = (MarketApp) appDao.findById(appId, MarketApp.class);

            //check app must is duplicate
            boolean duplicate = appDao.isAppMustSet(appId);

            JSONObject o = new JSONObject();
            if (duplicate) {
                o.put("appMustId", -1);
                return o.toJSONString();
            } else {
                AppMust appMust = new AppMust(install, app);
                appDao.saveOrUpdate(appMust);

                //reset the cache
                AppMustDTO dto = AppMustWebAssember.toAppMustDTODTO(appMust);
                cacheService.resetAppMustInCache(dto, false);

                o.put("appMustId", appMust.getId());
                return o.toJSONString();
            }
        } catch (Exception e) {
            logger.error("save app mustfailed", e);
            return null;
        }
    }

    public void deleteAppMust(int appMustId) {
        AppMust appMust = (AppMust) appDao.findById(appMustId, AppMust.class);

        //reset the cache
        AppMustDTO dto = AppMustWebAssember.toAppMustDTODTO(appMust);
        cacheService.resetAppMustInCache(dto, true);

        appDao.delete(appMust);
    }
}
