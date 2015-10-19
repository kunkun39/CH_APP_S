package com.changhong.system.web.facade.assember;

import com.changhong.client.service.ClientServiceImpl;
import com.changhong.system.domain.BoxRecommend;
import com.changhong.system.domain.MarketApp;
import com.changhong.system.web.facade.dto.BoxRecommendDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-7
 * Time: 上午10:20
 */
public class BoxRecommendWebAssember {

    public static BoxRecommendDTO toBoxRecommendDTO(BoxRecommend recommend) {
        final int id = recommend.getId();
        final int pageNumber = recommend.getPageNumber();
        final int recommendPosition = recommend.getRecommendPosition();

        MarketApp app = recommend.getMarketApp();
        MarketApp tmpApp = recommend.getTmpMarketApp();
        BoxRecommendDTO dto = null;

        if (tmpApp != null) {
            final int marketAppId = tmpApp.getId();
            final String appFullName = tmpApp.getAppName();
            final String appKey = tmpApp.getAppKey();
            final String posterActualFileName = tmpApp.getAppPoster().getActualFileName();
            final String iconActualFileName = tmpApp.getAppIcon().getActualFileName();

            dto = new BoxRecommendDTO(id, pageNumber, recommendPosition, marketAppId, appKey, appFullName, recommendPosition <= ClientServiceImpl.CLIENT_POST_SIZE ? posterActualFileName : iconActualFileName);
        } else if (app != null) {
            final int marketAppId = app.getId();
            final String appFullName = app.getAppName();
            final String appKey = app.getAppKey();
            final String posterActualFileName = app.getAppPoster().getActualFileName();
            final String iconActualFileName = app.getAppIcon().getActualFileName();

            dto = new BoxRecommendDTO(id, pageNumber, recommendPosition, marketAppId, appKey, appFullName, recommendPosition <= ClientServiceImpl.CLIENT_POST_SIZE ? posterActualFileName : iconActualFileName);
        } else {
            dto =  new BoxRecommendDTO(id, pageNumber, recommendPosition);
        }

        return dto;
    }

    public static List<BoxRecommendDTO> toBoxRecommendDTOList(List<BoxRecommend> recommends) {
        List<BoxRecommendDTO> dtos = new ArrayList<BoxRecommendDTO>();
        if (recommends != null) {
            for (BoxRecommend recommend : recommends) {
                dtos.add(toBoxRecommendDTO(recommend));
            }
        }
        return dtos;
    }
}
