package com.changhong.system.domain;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.web.facade.dto.MarketAppDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午3:07
 */
public class DomainHelper {

    public static AppChangeHistory generateAppChangeHistories(MarketApp app, MarketAppDTO dto) {
        AppChangeHistory history = new AppChangeHistory();
        if (app.getId() <= 0) {
            AppChangeDetails details = new AppChangeDetails("NEW", "");
            history.addChangeDetails(details);
        } else {
            if (!app.getAppName().equals(dto.getAppFullName())) {
                AppChangeDetails details = new AppChangeDetails("应用名称", app.getAppName() + " -> " + dto.getAppFullName());
                history.addChangeDetails(details);
            }
            if (app.getAppScores() != dto.getAppScores()) {
                AppChangeDetails details = new AppChangeDetails("应用评分", app.getAppScores() + " -> " + dto.getAppScores());
                history.addChangeDetails(details);
            }
            if (app.getAppVersionInt() != Integer.valueOf(dto.getAppVersionInt())) {
                AppChangeDetails details = new AppChangeDetails("应用数字版本", app.getAppVersionInt() + " -> " + dto.getAppVersionInt());
                history.addChangeDetails(details);
            }
            if (!app.getAppVersion().equals(dto.getAppVersion())) {
                AppChangeDetails details = new AppChangeDetails("应用版本", app.getAppVersion() + " -> " + dto.getAppVersion());
                history.addChangeDetails(details);
            }
            if (!app.getAppPackage().equals(dto.getAppPackage())) {
                AppChangeDetails details = new AppChangeDetails("应用包名", app.getAppPackage() + " -> " + dto.getAppPackage());
                history.addChangeDetails(details);
            }
            if (app.isRecommend() != dto.isRecommend()) {
                AppChangeDetails details = new AppChangeDetails("应用推荐", "改变推荐的状态");
                history.addChangeDetails(details);
            }
            if (dto.getIconFile() != null && dto.getIconFile().getSize() > 0) {
                AppChangeDetails details = new AppChangeDetails("应用LOGO", "上传新的应用LOGO");
                history.addChangeDetails(details);
            }
            if (dto.getPosterFile() != null && dto.getPosterFile().getSize() > 0) {
                AppChangeDetails details = new AppChangeDetails("应用海报", "上传新的应用海报");
                history.addChangeDetails(details);
            }
            if (dto.getApkFile() != null && dto.getApkFile().getSize() > 0) {
                AppChangeDetails details = new AppChangeDetails("应用程序文件", "上传新的应用程序文件");
                history.addChangeDetails(details);
            }
            if (app.getAppCategory().getId() != dto.getCategoryId()) {
                AppCategory oldCategory = (AppCategory) EntityLoadHolder.getUserDao().findById(dto.getCategoryId(), AppCategory.class);
                AppChangeDetails details = new AppChangeDetails("应用类别", app.getAppCategory().getCategoryName() + " -> " + oldCategory.getCategoryName());
                history.addChangeDetails(details);
            }
            if (!dto.getAddTopics().equals(dto.getTopicIds())) {
                AppChangeDetails details = new AppChangeDetails("应用专题", "修改应用专题");
                history.addChangeDetails(details);
            }
        }

        return history;
    }

    public static AppChangeHistory generateStatusChangeHistory(MarketApp app, String oldStatus, String newStatus) {
        AppChangeHistory history = new AppChangeHistory();
        User currentUser = new User();
        currentUser.setId(SecurityUtils.currectUserId());
        history.setUser(currentUser);
        history.setApp(app);

        AppChangeDetails details = new AppChangeDetails("状态", AppStatus.valueOf(oldStatus).getDescription() + " -> " + AppStatus.valueOf(newStatus).getDescription());
        history.addChangeDetails(details);
        return history;
    }
}
