package com.changhong.client.dao;

import com.changhong.system.domain.AppDownloadHistory;

import java.util.HashMap;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:16
 */
public interface ClientDao {

    List<HashMap> loadAllAppCategoryInfo();

    List<HashMap> loadAllAppTopicInfo();

    List<HashMap> loadAllBoxPages();

    List<HashMap> loadRankList(int type);

    void updateAppDownloadTime(AppDownloadHistory history);

    List<HashMap> loadAppDetailsRecommend(int categoryId);

    boolean insertBackupAppInfo(String boxMac, String appIDs);

    int updateBackupAppInfo(String boxMac, String appIDs);

    String loadBackupAppInfo(String boxMac);
}
