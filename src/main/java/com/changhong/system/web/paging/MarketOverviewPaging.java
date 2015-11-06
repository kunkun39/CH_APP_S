package com.changhong.system.web.paging;

import com.changhong.system.domain.AppCategory;
import com.changhong.system.service.AppService;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.MarketAppDTO;
import com.changhong.system.web.facade.dto.UserDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class MarketOverviewPaging extends AbstractPaging<MarketAppDTO> {

    private AppService appService;

    private String appName;
    private int categoryId;
    private int topicId;
    private String appStatus;

    public MarketOverviewPaging(AppService appService) {
        this.appService = appService;
    }

    public List<MarketAppDTO> getItems() {
        return appService.obtainMarketApps(appName, categoryId, topicId, appStatus, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = appService.obtainMarketAppSize(appName, categoryId, topicId, appStatus);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&appName=" + getAppName() + "&categoryId=" + getCategoryId() + "&topicId=" + getTopicId()  + "&appStatus=" + getAppStatus();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}

