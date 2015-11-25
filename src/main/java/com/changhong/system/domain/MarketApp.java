package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.pinying.PinyinFormat;
import com.changhong.common.pinying.PinyinHelper;
import com.changhong.common.utils.CHStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 下午2:27
 */
public class MarketApp extends EntityBase {

    private static final Log logger = LogFactory.getLog(MarketApp.class);

    private String appKey;

    private String appName;

    private String appDescription;

    private String appNote;

    private int appVersionInt;

    private String appVersion;

    private String appPackage;

    private int appScores;

    private int downloadTimes;

    private String appSize;

    private String pinYingShort;

    private String pinYingFull;

    private DateTime createTime;

    private AppStatus appStatus;

    private boolean recommend;

    private AppIcon appIcon;

    private AppFile appFile;

    private AppPoster appPoster;

    private AppCategory appCategory;

    private List<AppTopic> appTopics;

    /**
     * just temp used remeber change history
     */
    private AppChangeHistory history;

    public MarketApp() {
    }

    public MarketApp(String appName, String appDescription, String appNote, int appScores, boolean recommend) {
        this.appKey = CHStringUtils.getRandomString(8);
        this.appName = appName;
        this.appDescription = appDescription;
        this.appNote = appNote;
        this.downloadTimes = 0;
        this.appStatus = AppStatus.CREAETED;
        this.createTime = new DateTime();
        this.appScores = appScores;
        this.recommend = recommend;

        //生成拼音
        try {
            this.pinYingFull = PinyinHelper.convertToPinyinString(appName, "", PinyinFormat.WITHOUT_TONE).toUpperCase();
            this.pinYingShort = PinyinHelper.getShortPinyin(appName).toUpperCase();
        } catch (Exception e) {
            logger.error("new app can't convert " + appName + " to pingying", e);
        }
    }

    public void resetTopic(String addTopics) {
        if (this.appTopics == null) {
            this.appTopics = new ArrayList<AppTopic>();
        } else {
            this.appTopics.clear();
        }

        String[] resets = StringUtils.delimitedListToStringArray(addTopics, ",");
        if (resets.length > 0) {
            for (String reset : resets) {
                if (StringUtils.hasText(reset)) {
                    AppTopic topic = new AppTopic(Integer.valueOf(reset));
                    this.appTopics.add(topic);
                }
            }
        }
    }

    public void resetUpdateTime() {
        setTimestamp(new Date());
    }

    public void resetPingYin() {
        //生成拼音
        try {
            this.pinYingFull = PinyinHelper.convertToPinyinString(appName, "", PinyinFormat.WITHOUT_TONE).toUpperCase();
            this.pinYingShort = PinyinHelper.getShortPinyin(appName).toUpperCase();
        } catch (Exception e) {
            logger.error("edit app can't convert " + appName + " to pingying", e);
        }
    }

    /********************************************GET/SET***********************************************/

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppNote() {
        return appNote;
    }

    public void setAppNote(String appNote) {
        this.appNote = appNote;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public int getAppScores() {
        return appScores;
    }

    public void setAppScores(int appScores) {
        this.appScores = appScores;
    }

    public int getAppVersionInt() {
        return appVersionInt;
    }

    public void setAppVersionInt(int appVersionInt) {
        this.appVersionInt = appVersionInt;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }

    public String getPinYingShort() {
        return pinYingShort;
    }

    public void setPinYingShort(String pinYingShort) {
        this.pinYingShort = pinYingShort;
    }

    public String getPinYingFull() {
        return pinYingFull;
    }

    public void setPinYingFull(String pinYingFull) {
        this.pinYingFull = pinYingFull;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public AppIcon getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(AppIcon appIcon) {
        this.appIcon = appIcon;
    }

    public AppFile getAppFile() {
        return appFile;
    }

    public void setAppFile(AppFile appFile) {
        this.appFile = appFile;
    }

    public AppPoster getAppPoster() {
        return appPoster;
    }

    public void setAppPoster(AppPoster appPoster) {
        this.appPoster = appPoster;
    }

    public AppCategory getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(AppCategory appCategory) {
        this.appCategory = appCategory;
    }

    public List<AppTopic> getAppTopics() {
        return appTopics;
    }

    public void setAppTopics(List<AppTopic> appTopics) {
        this.appTopics = appTopics;
    }

    public AppChangeHistory getHistory() {
        return history;
    }

    public void setHistory(AppChangeHistory history) {
        this.history = history;
    }
}
