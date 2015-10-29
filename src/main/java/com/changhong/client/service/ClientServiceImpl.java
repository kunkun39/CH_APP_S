package com.changhong.client.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.dao.ClientDao;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.AppDownloadHistory;
import com.changhong.system.web.facade.dto.AppMustDTO;
import com.changhong.system.web.facade.dto.LuncherRecommendDTO;
import com.changhong.system.web.facade.dto.MarketAppDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:15
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    public final static int CLIENT_POST_SIZE = 6;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ClientDao clientDao;

    @Value("${application.file.request.path}")
    private String fileRequestHost;

    private int clientVersion = 1;

    private long lastRequestClientVersionTime = 0L;

    private final static int versionCheckDuring = 1000 * 60 * 10;

    @Value("${application.appmarket.update.path}")
    private String appMarketApkUpdateURL = "";

    public String obtainBootImage(){
        //把开机图片组合成JSON数据流
        JSONObject all = new JSONObject();
        all.put("host", fileRequestHost);
        all.put("boot_img", cacheService.getBootImageFileName());
        return all.toJSONString();
    }

    public String obtainAllAppCategoryInfo() {

        //获得所有的应用总类信息，使用到了泛型
        List<HashMap> values =  clientDao.loadAllAppCategoryInfo();

        //声明一个JSON对象，向JSON数据流中添加数据
        JSONObject all = new JSONObject();
        all.put("host", fileRequestHost);

        //一个小时访问一次
        long currentRequestClientVersionTime = System.currentTimeMillis();
        if ((currentRequestClientVersionTime - lastRequestClientVersionTime) > versionCheckDuring) {
            lastRequestClientVersionTime = currentRequestClientVersionTime;
            clientVersion = clientDao.loadClientVersion();
        }
        all.put("client_v", clientVersion);
        all.put("client_url", appMarketApkUpdateURL);

        JSONArray array = new JSONArray();
        if (values != null) {
            //foreach循环访问集合里的元素
            for (HashMap value : values) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.CATEGORY_ID, (Integer) value.get("category_id") + "");
                single.put(ClientInfoProperties.CATEGORY_NAME, (String) value.get("category_name"));
                single.put(ClientInfoProperties.CATEGORY_PARENTID, value.get("parent_id") == null ? "-1" : (Integer) value.get("parent_id") + "");
                single.put(ClientInfoProperties.CATEGORY_FILENAME, value.get("actual_filename") == null ? "" : (String) value.get("actual_filename"));

                //向array集合里添加一个元素
                array.add(single);
            }
        }
        all.put("values", array);

        return all.toJSONString();
    }



    public String obtainBoxIndexPageInfo() {
        List<HashMap> values =  clientDao.loadAllBoxPages();

        JSONObject page = new JSONObject();
        page.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (values != null) {
            for (HashMap value : values) {
                JSONObject single = new JSONObject();
                single.put("page", (Integer) value.get("page_number") + "");
                Integer position = (Integer) value.get("recommend_position");
                single.put("position", position);

                Object recommendAppId = value.get("recommend_app_id");
                int appId = recommendAppId == null ? -1 : (Integer)recommendAppId;
                if (appId > 0) {
                    MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
                    single.put(ClientInfoProperties.APP_ID, dto.getId());
                    single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
                    single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                    //小于三传海报，大于3传ICON
                    if (position <= ClientServiceImpl.CLIENT_POST_SIZE) {
                        single.put(ClientInfoProperties.APP_POSTER_FILEPATH, dto.getPosterActualFileName());
                    } else {
                        single.put(ClientInfoProperties.APP_POSTER_FILEPATH, dto.getIconActualFileName());
                    }
                } else {
                    single.put(ClientInfoProperties.APP_ID, "-1");
                }

                all.add(single);
            }
        }

        page.put("pages", all);
        return page.toJSONString();
    }

    public String obtainCategoryApps(int categoryId) {
        List<MarketAppDTO> apps = cacheService.obtainCachedAppByCategoryId(categoryId);

        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (apps != null) {
            for (MarketAppDTO dto : apps) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getId());
                single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
                single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
                single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
                single.put(ClientInfoProperties.APP_RECOMMEND, dto.isRecommend());
                single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
                all.add(single);
            }
        }

        values.put("values", all);
        return values.toJSONString();
    }

    public String obtainAppDetailsInfo(int appId) {
        MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
        JSONObject app = new JSONObject();

        if (dto != null) {
            app.put("host", fileRequestHost);
            app.put(ClientInfoProperties.APP_ID, dto.getId());
            app.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
            app.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
            app.put(ClientInfoProperties.APP_VERSION_INT, dto.getAppVersionInt());
            app.put(ClientInfoProperties.APP_VERSION, dto.getAppVersion());
            app.put(ClientInfoProperties.APP_PACKAGE, dto.getAppPackage());
            app.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
            app.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
            app.put(ClientInfoProperties.APP_DOWNLOAD, dto.getDownloadTimes());
            app.put(ClientInfoProperties.APP_UPDATE_DATE, dto.getUpdateDate());
            app.put(ClientInfoProperties.APP_RECOMMEND, dto.isRecommend());
            app.put(ClientInfoProperties.APP_DESCRIPTION, dto.getAppDescription());
            app.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
            app.put(ClientInfoProperties.APP_APK_FILEPATH, dto.getApkActualFileName());
            app.put(ClientInfoProperties.APP_POSTER_FILEPATH, dto.getPosterActualFileName());
            app.put(ClientInfoProperties.APP_CATEGORY_ID, dto.getCategoryId());
        }

        return app.toJSONString();
    }

    public String obtainAppDetailsRecommend(int categoryId) {
        JSONObject all = new JSONObject();
        all.put("host", fileRequestHost);

        //获得最新的APPS
        JSONArray array = new JSONArray();
        List<HashMap> newest = clientDao.loadAppDetailsRecommend(categoryId);
        for (HashMap hashMap : newest) {
            int appId = (Integer) hashMap.get("id");
            MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
            JSONObject single = new JSONObject();
            single.put(ClientInfoProperties.APP_ID, dto.getId());
            single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
            single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
            single.put(ClientInfoProperties.APP_RECOMMEND, dto.isRecommend());
            single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
            array.add(single);
        }
        all.put("values", array);

        return all.toJSONString();
    }

    public String obtainSearchApps(String keyWords) {
        List<MarketAppDTO> apps = cacheService.obtainSearchApps(keyWords);

        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (apps != null) {
            for (MarketAppDTO dto : apps) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getId());
                single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
                single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
                single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
                single.put(ClientInfoProperties.APP_RECOMMEND, dto.isRecommend());
                single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
                all.add(single);
            }
        }

        values.put("values", all);
        return values.toJSONString();
    }

    public String obtainRankListApps() {
        JSONObject all = new JSONObject();
        all.put("host", fileRequestHost);

        //获得最新的APPS
        JSONArray newestArray = new JSONArray();
        List<HashMap> newest = clientDao.loadRankList(1);
        for (HashMap hashMap : newest) {
            int appId = (Integer) hashMap.get("id");
            MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
            JSONObject single = new JSONObject();
            single.put(ClientInfoProperties.APP_ID, dto.getId());
            single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
            single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
            single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
            single.put(ClientInfoProperties.APP_DOWNLOAD, dto.getDownloadTimes());
            single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
            single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
            newestArray.add(single);
        }
        all.put("NEWEST", newestArray);

        //获得最热的APPS
        JSONArray hotestArray = new JSONArray();
        List<HashMap> hotest = clientDao.loadRankList(2);
        for (HashMap hashMap : hotest) {
            int appId = (Integer) hashMap.get("id");
            MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
            JSONObject single = new JSONObject();
             single.put(ClientInfoProperties.APP_ID, dto.getId());
            single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
            single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
            single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
            single.put(ClientInfoProperties.APP_DOWNLOAD, dto.getDownloadTimes());
            single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
            single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
            hotestArray.add(single);
        }
        all.put("HOTEST", hotestArray);

        //获得最快增长的APPS
        JSONArray fastestArray = new JSONArray();
        List<HashMap> fastest = clientDao.loadRankList(3);
        for (HashMap hashMap : fastest) {
            int appId = (Integer) hashMap.get("app_id");
            MarketAppDTO dto = cacheService.obtainMarketAppInCache(appId);
            JSONObject single = new JSONObject();
             single.put(ClientInfoProperties.APP_ID, dto.getId());
            single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
            single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
            single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
            single.put(ClientInfoProperties.APP_DOWNLOAD, dto.getDownloadTimes());
            single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
            single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
            fastestArray.add(single);
        }
        all.put("FASTEST", fastestArray);

        return all.toJSONString();
    }

    public synchronized String obtainAppsVersions(String[] appPackages) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        apps.addAll(cacheService.obtainMarketAppInCache(appPackages));

        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (apps != null) {
            for (MarketAppDTO dto : apps) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getId());
                single.put(ClientInfoProperties.APP_PACKAGE, dto.getAppPackage());
                single.put(ClientInfoProperties.APP_VERSION_INT, dto.getAppVersionInt());
                all.add(single);
            }
        }

        values.put("values", all);
        return values.toJSONString();
    }

    public synchronized void updateAppDownloadTime(int appId, String boxMac) {
        int categoryId = cacheService.obtainAppCategoryId(appId);
        int categoryFatherId = cacheService.obtainCategoryFatherId(categoryId);

        cacheService.updateAppDownloadTimes(appId);

        AppDownloadHistory history = new AppDownloadHistory(boxMac, appId, categoryFatherId, categoryId);
        clientDao.updateAppDownloadTime(history);
    }

    public String obtainLuncherAppRecommends() {
        List<LuncherRecommendDTO> dtos = cacheService.obtainLuncherRecommends();

        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (dtos != null) {
            for (LuncherRecommendDTO dto : dtos) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getAppId());
                single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                single.put(ClientInfoProperties.APP_NAME, dto.getAppName());
                single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
                all.add(single);
            }
        }

        values.put("values", all);
        return values.toJSONString();
    }

    public String obtainAppMusts() {
        List<AppMustDTO> dtos = cacheService.obtainAppMust();

        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();
        if (dtos != null) {
            for (AppMustDTO dto : dtos) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getAppId());
                single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                single.put(ClientInfoProperties.APP_PACKAGE, dto.getPackageName());
                single.put(ClientInfoProperties.APP_VERSION_INT, dto.getAppVersionInt());
                single.put(ClientInfoProperties.APP_APK_FILEPATH, dto.getApkActualFileName());
                single.put("install", dto.isInstall());
                all.add(single);
            }
        }

        values.put("values", all);
        return values.toJSONString();
    }

    /*************************************************云备份相关********************************************************/

    public String checkBackupApp(String[] appPackages, String boxMac) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        apps.addAll(cacheService.obtainMarketAppInCache(appPackages));

        JSONObject values = new JSONObject();
        JSONArray all = new JSONArray();

        String appIdInfo = clientDao.loadBackupAppInfo(boxMac);
        HashSet<String> hashSet = new HashSet<String>();
        if(appIdInfo != null) {
            String[] backupAppIds = appIdInfo.split(",");
            for(String backupAppId : backupAppIds) {
                hashSet.add(backupAppId);
            }
        }

        if (apps != null) {
            for (MarketAppDTO dto : apps) {
                JSONObject single = new JSONObject();
                single.put(ClientInfoProperties.APP_ID, dto.getId());
                single.put(ClientInfoProperties.APP_PACKAGE, dto.getAppPackage());
                single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
                single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
                single.put(ClientInfoProperties.APP_IS_BACKUP, hashSet.contains(String.valueOf(dto.getId())) ? 1 : 0);
                all.add(single);
            }
            values.put("checkbackupapps", all);
        }
        else {
            values.put("checkbackupapps", "package error");
        }
        return values.toJSONString();
    }

    public String deleteBackupApps(String appIds, String boxMac) {
        JSONObject values = new JSONObject();
        StringBuilder builder = new StringBuilder();
        String[] requestAppIds = appIds.split(",");

        JSONArray all = new JSONArray();

        String appIdInfo = clientDao.loadBackupAppInfo(boxMac);
        if(appIdInfo != null) {
            HashSet<String> hashSet = new HashSet<String>();

            String[] backupAppIds = appIdInfo.split(",");
            for(String backupAppId : backupAppIds) {
                hashSet.add(backupAppId);
            }

            for(String appId : requestAppIds) {
                if(hashSet.remove(appId)) {
                    JSONObject single = new JSONObject();
                    single.put(ClientInfoProperties.APP_ID, appId);
                    all.add(single);
                }
            }
            Iterator i = hashSet.iterator();
            while(i.hasNext()) {
                builder.append(i.next() + ",");
            }
            if(builder.length() > 1) {
                builder.deleteCharAt(builder.length()-1);
            }
            if(clientDao.updateBackupAppInfo(boxMac, builder.toString()) >  0) {
                values.put("deletebackupapps", all);
            }
        }
        else {
            values.put("deletebackupapps", "no values");
        }
        return values.toJSONString();
    }

    public String obtainBackupApps(String boxMac) {
        JSONObject values = new JSONObject();
        values.put("host", fileRequestHost);

        JSONArray all = new JSONArray();

        String appIdInfo = clientDao.loadBackupAppInfo(boxMac);

        if(appIdInfo != null) {
            String[] appIds = appIdInfo.split(",");
            for(String appId : appIds) {
                MarketAppDTO dto = cacheService.obtainMarketAppInCache(Integer.parseInt(appId));
                    if(dto != null) {
                        JSONObject single = new JSONObject();
                        single.put(ClientInfoProperties.APP_ID, dto.getId());
                        single.put(ClientInfoProperties.APP_NAME, dto.getAppFullName());
                        single.put(ClientInfoProperties.APP_KEY, dto.getAppKey());
                        single.put(ClientInfoProperties.APP_SCORES, dto.getAppScores());
                        single.put(ClientInfoProperties.APP_SIZE, dto.getAppSize());
                        single.put(ClientInfoProperties.APP_PACKAGE, dto.getAppPackage());
                        single.put(ClientInfoProperties.APP_ICON_FILEPATH, dto.getIconActualFileName());
                        single.put(ClientInfoProperties.APP_APK_FILEPATH, dto.getApkActualFileName());
                        all.add(single);
                    }
            }
            values.put("getbackupapps", all);
        }
        else {
            values.put("getbackupapps", "no values");
        }

        return values.toJSONString();
    }

    public String requestBackupApps(String appIds, String boxMac) {
        JSONObject values = new JSONObject();

        StringBuilder builder = new StringBuilder();
        String[] requestAppIds = appIds.split(",");

        JSONArray all = new JSONArray();
        String appIdInfo = clientDao.loadBackupAppInfo(boxMac);
        if(appIdInfo != null) {
            HashSet<String> hashSet = new HashSet<String>();

            String[] backupAppIds = appIdInfo.split(",");
            for(String backupAppId : backupAppIds) {
                hashSet.add(backupAppId);
            }

            for(String appId : requestAppIds) {
                if(hashSet.add(appId)) {
                    JSONObject single = new JSONObject();
                    single.put(ClientInfoProperties.APP_ID, appId);
                    all.add(single);
                }
            }

            Iterator i = hashSet.iterator();
            while(i.hasNext()) {
                builder.append(i.next() + ",");
            }
        }
        else {
            for(String appId : requestAppIds) {
               JSONObject single = new JSONObject();
               single.put(ClientInfoProperties.APP_ID, appId);
               all.add(single);

               builder.append(appId + ",");
            }
        }
        if(builder.length() > 1) {
            builder.deleteCharAt(builder.length()-1);
        }

        if(0 == clientDao.updateBackupAppInfo(boxMac, builder.toString())) {
            if(clientDao.insertBackupAppInfo(boxMac, builder.toString())) {
                values.put("requestbackupapps", all);
            }
            else {
                values.put("requestbackupapps", "insert error");
            }
        }
        else {
            values.put("requestbackupapps", all);
        }

        return values.toJSONString();
    }
}
