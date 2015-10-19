package com.changhong.system.service;

import com.changhong.system.domain.CategoryIcon;
import com.changhong.system.domain.MarketApp;

/**
 * User: Jack Wang
 * Date: 15-7-31
 * Time: 下午1:28
 */
public interface DocumentService {

    void uploadAppIconData(MarketApp app);

    void uploadAppApkData(MarketApp app);

    void uploadAppPosterData(MarketApp app);

    void uploadCategoryIconData(CategoryIcon icon);

    void deleteCategoryIconData(String filename);
}
