package com.changhong.system.web.dwr;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.NumberUtils;
import com.changhong.system.service.AppService;
import com.changhong.system.service.StatisticService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("systemDWRHandler")
public class SystemDWRHandler {

    @Autowired
    private AppService appService;

    @Autowired
    private StatisticService statisticService;

    public boolean obtainCategoryHasApps(int categoryId) {
        return appService.obtainCategoryHasApps(categoryId);
    }

    public String obtainPageAppsByStartNumber(int startNumber) throws JSONException {
        return appService.obtainPageAppsByStartNumber(startNumber).toString();
    }

    public String obtainAppDownloadColumnData(int categoryId, int year, int month) throws JSONException {
        return statisticService.obtainStatisDataForColumn(categoryId, year, month).toString();
    }

    public String obtainAppDownloadPienData(int categoryId, int year, int month) throws JSONException {
        return statisticService.obtainStatisDataForPie(categoryId, year, month).toString();
    }

    public String obtainRecommendApps(int appId, int categoryId, String appName) throws JSONException {
        return appService.obtainRecommendApps(appId, categoryId, appName).toString();
    }

    public boolean updateBoxRecommendPosition(int pageNumber, int recommendPosition, int marketAppId, int currentUserId) {
        return appService.updateBoxRecommendPosition(pageNumber, recommendPosition, marketAppId, currentUserId);
    }

    public String updateLuncherRecommendPosition(int appId) {
        return appService.updateLuncherRecommendPosition(appId);
    }

    public void deleteLuncherRecommendPosition(int recommendId) {
        appService.deleteLuncherRecommendPosition(recommendId);
    }

    public int caculateClientNumber(String clientFrom, String clientTo) {
        try {
            String[] tokens1 = StringUtils.delimitedListToStringArray(clientFrom, ":");
            String[] tokens2 = StringUtils.delimitedListToStringArray(clientTo, ":");
            if (tokens1.length != tokens2.length || tokens1.length != 6) {
                return -1;
            }

            clientFrom = clientFrom.toLowerCase().replace(":", "");
            clientTo = clientTo.toLowerCase().replace(":", "");
            int from = NumberUtils.hexStr2Number(clientFrom);
            int to = NumberUtils.hexStr2Number(clientTo);
            return from - to;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String updateAppMust(int appId, boolean install) {
        return appService.updateAppMust(appId, install);
    }

    public void deleteAppMust(int appMustId) {
        appService.deleteAppMust(appMustId);
    }

}
