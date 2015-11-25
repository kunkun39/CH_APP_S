package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.*;
import com.changhong.system.web.facade.dto.AppTopicDTO;
import com.changhong.system.web.facade.dto.MarketAppDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:41
 */
public class MarketAppWebAssember {

    public static MarketApp toMarkAppDomain(MarketAppDTO dto) {
        AppChangeHistory history = null;

        MarketApp app = null;
        int appId = dto.getId();
        if (appId > 0) {
            app = (MarketApp) EntityLoadHolder.getUserDao().findById(appId, MarketApp.class);

            history = DomainHelper.generateAppChangeHistories(app, dto);

            app.setAppName(dto.getAppFullName().trim());
            app.setAppDescription(dto.getAppDescription());
            app.setAppNote(dto.getAppNote());
            app.setAppScores(dto.getAppScores());
            app.setRecommend(dto.isRecommend());
        } else {
            app = new MarketApp(dto.getAppFullName().trim(), dto.getAppDescription(), dto.getAppNote(), dto.getAppScores(), dto.isRecommend());
            history = DomainHelper.generateAppChangeHistories(app, dto);
        }

        app.resetTopic(dto.getAddTopics());

        if (app.getAppCategory() == null || app.getAppCategory().getId() != dto.getCategoryId()) {
            AppCategory category = new AppCategory();
            category.setId(dto.getCategoryId());
            app.setAppCategory(category);
        }

        app.setHistory(history);
        return app;
    }

    public static MarketAppDTO toMarketAppDTO(MarketApp marketApp) {
        final int id = marketApp.getId();
        final String appKey = marketApp.getAppKey();
        final String appName = marketApp.getAppName();
        final String appDescription = marketApp.getAppDescription();
        final String appNote = marketApp.getAppNote();
        final String appVersionInt = marketApp.getAppVersionInt() + "";
        final String appVersion = marketApp.getAppVersion();
        final String appPackage = marketApp.getAppPackage();
        final int appScores = marketApp.getAppScores();
        final int downloadTimes = marketApp.getDownloadTimes();
        final String appSize = Document.getFileSize(marketApp.getAppSize());
        final String status = marketApp.getAppStatus().name();
        final String statusName = marketApp.getAppStatus().getDescription();
        final boolean recommend = marketApp.isRecommend();

        AppCategory category = marketApp.getAppCategory();
        final int categoryId = category.getId();
        final String categoryName = category.getCategoryName();
        final String fullCategoryName = category.getFullCategoryPath();

        AppIcon appIcon = marketApp.getAppIcon();
        final int appIconId = appIcon.getId();
        final String iconActualFileName = appIcon.getActualFileName();
        final String iconFakeFileName = appIcon.getUploadFileName();

        AppFile appFile = marketApp.getAppFile();
        final int appFileId = appFile.getId();
        final String apkActualFileName = appFile.getActualFileName();
        final String apkFakeFileName = appFile.getUploadFileName();

        AppPoster posterFile = marketApp.getAppPoster();
        final int posterFileId = posterFile.getId();
        final String posterActualFileName = posterFile.getActualFileName();
        final String posterFakeFileName = posterFile.getUploadFileName();

        final String updateDate = CHDateUtils.getSimpleDateFormat(marketApp.getTimestamp());
        final String pinyingShort = marketApp.getPinYingShort();
        final String pinyingFull = marketApp.getPinYingFull();

        List<AppTopicDTO> topics = AppTopicWebAssember.toAppTopicDTOList(marketApp.getAppTopics());

        MarketAppDTO dto =  new MarketAppDTO(id, appKey, appName, appDescription, appNote, appVersionInt, appVersion, appPackage,
                appScores, downloadTimes, appSize, status, statusName, recommend, categoryId, categoryName, fullCategoryName,
                appIconId, iconActualFileName, iconFakeFileName,
                appFileId, apkActualFileName, apkFakeFileName,
                posterFileId, posterActualFileName, posterFakeFileName,
                updateDate, pinyingShort, pinyingFull, topics);

        return dto;
    }

    public static List<MarketAppDTO> toMarketAppDTOList(List<MarketApp> apps) {
        List<MarketAppDTO> dtos = new ArrayList<MarketAppDTO>();
        if (apps != null) {
            for (MarketApp app : apps) {
                dtos.add(toMarketAppDTO(app));
            }
        }
        return dtos;
    }
}
