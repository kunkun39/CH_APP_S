package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-9-29
 * Time: 上午9:40
 */
public class AppMust extends EntityBase {

    //ture stand for must install else stand for uninstall
    private boolean install;

    private MarketApp marketApp;

    public AppMust() {
    }

    public AppMust(boolean install, MarketApp marketApp) {
        this.install = install;
        this.marketApp = marketApp;
    }

    public boolean isInstall() {
        return install;
    }

    public void setInstall(boolean install) {
        this.install = install;
    }

    public MarketApp getMarketApp() {
        return marketApp;
    }

    public void setMarketApp(MarketApp marketApp) {
        this.marketApp = marketApp;
    }
}
