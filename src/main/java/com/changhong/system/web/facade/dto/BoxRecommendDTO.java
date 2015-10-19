package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 15-8-7
 * Time: 上午10:20
 */
public class BoxRecommendDTO {

    private int id;

    private int pageNumber;

    private int recommendPosition;

    private int marketAppId;

    private String appKey;

    private String appFullName;

    private String posterActualFileName;

    public BoxRecommendDTO() {
    }

    public BoxRecommendDTO(int id, int pageNumber, int recommendPosition) {
        this.id = id;
        this.pageNumber = pageNumber;
        this.recommendPosition = recommendPosition;
    }

    public BoxRecommendDTO(int id, int pageNumber, int recommendPosition,
                           int marketAppId, String appKey, String appFullName, String posterActualFileName) {
        this.id = id;
        this.pageNumber = pageNumber;
        this.recommendPosition = recommendPosition;

        this.marketAppId = marketAppId;
        this.appKey = appKey;
        this.appFullName = appFullName;
        this.posterActualFileName = posterActualFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRecommendPosition() {
        return recommendPosition;
    }

    public void setRecommendPosition(int recommendPosition) {
        this.recommendPosition = recommendPosition;
    }

    public int getMarketAppId() {
        return marketAppId;
    }

    public void setMarketAppId(int marketAppId) {
        this.marketAppId = marketAppId;
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

    public String getPosterActualFileName() {
        return posterActualFileName;
    }

    public void setPosterActualFileName(String posterActualFileName) {
        this.posterActualFileName = posterActualFileName;
    }
}
