package com.changhong.system.web.facade.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 下午4:23
 */
public class MarketAppDTO {

    private int id;

    private String appKey;

    private String appFullName;

    private String appDescription;

    private String appNote;

    private String appVersionInt;

    private String appVersion;

    private String appPackage;

    private int appScores;

    private int downloadTimes;

    private String appSize;

    private String status;

    private String statusName;

    private boolean recommend;

    private int categoryId;

    private String categoryName;

    private String fullCategoryName;

    private int appIconId;

    private String iconActualFileName;

    private String iconFakeFileName;

    private int appFileId;

    private String apkActualFileName;

    private String apkFakeFileName;

    private int appPosterId;

    private String posterActualFileName;

    private String posterFakeFileName;

    private String updateDate;

    private String pinYingShort;

    private String pinYingFull;

    //files
    private MultipartFile iconFile;
    private MultipartFile apkFile;
    private MultipartFile posterFile;

    public MarketAppDTO() {
        this.appScores = 8;
    }

    public MarketAppDTO(int id, String appKey, String appName, String appDescription, String appNote, String appVersionInt, String appVersion, String appPackage,
                        int appScores, int downloadTimes, String appSize, String status, String statusName, boolean recommend,
                        int categoryId, String categoryName, String fullCategoryName,
                        int appIconId, String iconActualFileName, String iconFakeFileName,
                        int appFileId, String apkActualFileName, String apkFakeFileName,
                        int appPosterId, String posterActualFileName, String posterFakeFileName,
                        String updateDate, String pinYingShort, String pinYingFull) {
        this.id = id;
        this.appKey = appKey;
        this.appFullName = appName;
        this.appDescription = appDescription;
        this.appNote = appNote;
        this.appVersionInt = appVersionInt;
        this.appVersion = appVersion;
        this.appPackage = appPackage;
        this.appScores = appScores;
        this.downloadTimes = downloadTimes;
        this.appSize = appSize;
        this.status = status;
        this.statusName = statusName;
        this.recommend = recommend;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.fullCategoryName = fullCategoryName;
        this.appIconId = appIconId;
        this.iconActualFileName = iconActualFileName;
        this.iconFakeFileName = iconFakeFileName;
        this.appFileId = appFileId;
        this.apkActualFileName = apkActualFileName;
        this.apkFakeFileName = apkFakeFileName;
        this.appPosterId = appPosterId;
        this.posterActualFileName = posterActualFileName;
        this.posterFakeFileName = posterFakeFileName;
        this.updateDate = updateDate;
        this.pinYingShort = pinYingShort;
        this.pinYingFull = pinYingFull;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppFullName() {
        return appFullName;
    }

    public void setAppFullName(String appFullName) {
        this.appFullName = appFullName;
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

    public String getAppVersionInt() {
        return appVersionInt;
    }

    public void setAppVersionInt(String appVersionInt) {
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

    public int getAppScores() {
        return appScores;
    }

    public void setAppScores(int appScores) {
        this.appScores = appScores;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFullCategoryName() {
        return fullCategoryName;
    }

    public void setFullCategoryName(String fullCategoryName) {
        this.fullCategoryName = fullCategoryName;
    }

    public int getAppIconId() {
        return appIconId;
    }

    public void setAppIconId(int appIconId) {
        this.appIconId = appIconId;
    }

    public String getIconActualFileName() {
        return iconActualFileName;
    }

    public void setIconActualFileName(String iconActualFileName) {
        this.iconActualFileName = iconActualFileName;
    }

    public String getIconFakeFileName() {
        return iconFakeFileName;
    }

    public void setIconFakeFileName(String iconFakeFileName) {
        this.iconFakeFileName = iconFakeFileName;
    }

    public int getAppFileId() {
        return appFileId;
    }

    public void setAppFileId(int appFileId) {
        this.appFileId = appFileId;
    }

    public String getApkActualFileName() {
        return apkActualFileName;
    }

    public void setApkActualFileName(String apkActualFileName) {
        this.apkActualFileName = apkActualFileName;
    }

    public String getApkFakeFileName() {
        return apkFakeFileName;
    }

    public void setApkFakeFileName(String apkFakeFileName) {
        this.apkFakeFileName = apkFakeFileName;
    }

    public int getAppPosterId() {
        return appPosterId;
    }

    public void setAppPosterId(int appPosterId) {
        this.appPosterId = appPosterId;
    }

    public String getPosterActualFileName() {
        return posterActualFileName;
    }

    public void setPosterActualFileName(String posterActualFileName) {
        this.posterActualFileName = posterActualFileName;
    }

    public String getPosterFakeFileName() {
        return posterFakeFileName;
    }

    public void setPosterFakeFileName(String posterFakeFileName) {
        this.posterFakeFileName = posterFakeFileName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public MultipartFile getIconFile() {
        return iconFile;
    }

    public void setIconFile(MultipartFile iconFile) {
        this.iconFile = iconFile;
    }

    public MultipartFile getApkFile() {
        return apkFile;
    }

    public void setApkFile(MultipartFile apkFile) {
        this.apkFile = apkFile;
    }

    public MultipartFile getPosterFile() {
        return posterFile;
    }

    public void setPosterFile(MultipartFile posterFile) {
        this.posterFile = posterFile;
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
}
