package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-8-7
 * Time: 上午9:55
 */
public class BoxRecommend extends EntityBase {

    private int pageNumber;

    private int recommendPosition;

    private MarketApp marketApp;

    private MarketApp tmpMarketApp;

    public BoxRecommend() {
    }

    public BoxRecommend(int pageNumber, int recommendPosition) {
        this.pageNumber = pageNumber;
        this.recommendPosition = recommendPosition;
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

    public MarketApp getMarketApp() {
        return marketApp;
    }

    public void setMarketApp(MarketApp marketApp) {
        this.marketApp = marketApp;
    }

    public MarketApp getTmpMarketApp() {
        return tmpMarketApp;
    }

    public void setTmpMarketApp(MarketApp tmpMarketApp) {
        this.tmpMarketApp = tmpMarketApp;
    }
}
