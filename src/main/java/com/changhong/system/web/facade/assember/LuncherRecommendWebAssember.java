package com.changhong.system.web.facade.assember;

import com.changhong.system.domain.LuncherRecommend;
import com.changhong.system.domain.MarketApp;
import com.changhong.system.web.facade.dto.LuncherRecommendDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-25
 * Time: 下午3:06
 */
public class LuncherRecommendWebAssember {

    public static LuncherRecommendDTO toLuncherRecommendDTO(LuncherRecommend recommend) {
        final int id = recommend.getId();
        final int position = recommend.getPosition();
        MarketApp app = recommend.getMarketApp();
        final int appId = app.getId();
        final String appKey = app.getAppKey();
        final String appName = app.getAppName();
        final String appVersion = app.getAppVersion();
        final String iconActualFileName = app.getAppIcon().getActualFileName();

        return new LuncherRecommendDTO(id, position, appId, appName, appVersion, appKey, iconActualFileName);
    }

    public static List<LuncherRecommendDTO> toLuncherRecommendDTOList(List<LuncherRecommend> recommends) {
        List<LuncherRecommendDTO> dtos = new ArrayList<LuncherRecommendDTO>();
        if (recommends != null) {
            for (LuncherRecommend recommend : recommends) {
                dtos.add(toLuncherRecommendDTO(recommend));
            }
        }
        return dtos;
    }
}
