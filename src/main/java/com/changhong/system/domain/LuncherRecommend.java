package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-9-25
 * Time: 下午2:54
 */
public class LuncherRecommend extends EntityBase {

    private int position;

    private MarketApp marketApp;

    public LuncherRecommend() {
    }

    public LuncherRecommend(int position, MarketApp marketApp) {
        this.position = position;
        this.marketApp = marketApp;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MarketApp getMarketApp() {
        return marketApp;
    }

    public void setMarketApp(MarketApp marketApp) {
        this.marketApp = marketApp;
    }
}
