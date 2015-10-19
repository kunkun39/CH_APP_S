package com.changhong.system.web.facade.assember;

import com.changhong.system.domain.AppMust;
import com.changhong.system.domain.MarketApp;
import com.changhong.system.web.facade.dto.AppMustDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-25
 * Time: 下午3:06
 */
public class AppMustWebAssember {

    public static AppMustDTO toAppMustDTODTO(AppMust appMust) {
        final int id = appMust.getId();
        final boolean install = appMust.isInstall();
        MarketApp app = appMust.getMarketApp();
        final int appId = app.getId();
        final String appKey = app.getAppKey();
        final String appName = app.getAppName();
        final String appVersion = app.getAppVersion();
        final int appVersionInt = app.getAppVersionInt();
        final String iconActualFileName = app.getAppIcon().getActualFileName();
        final String packageName = app.getAppPackage();
        final String apkActualFileName = app.getAppFile().getActualFileName();

        return new AppMustDTO(id, install, appId, appName, appVersion, appVersionInt, appKey, iconActualFileName, packageName, apkActualFileName);
    }

    public static List<AppMustDTO> toAppMustDTOList(List<AppMust> appMusts) {
        List<AppMustDTO> dtos = new ArrayList<AppMustDTO>();
        if (appMusts != null) {
            for (AppMust app : appMusts) {
                dtos.add(toAppMustDTODTO(app));
            }
        }
        return dtos;
    }
}
