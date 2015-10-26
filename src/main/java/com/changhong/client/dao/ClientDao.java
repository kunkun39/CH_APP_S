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

    String loadClientBootImage();

    int loadClientVersion();

    List<HashMap> loadAllAppCategoryInfo();

    List<HashMap> loadAllBoxPages();

    List<HashMap> loadRankList(int type);

    void updateAppDownloadTime(AppDownloadHistory history);

    List<HashMap> loadAppDetailsRecommend(int categoryId);

    int insertBoxMacInfo(String boxMac);

    int loadMacIdByBoxMac(String boxMac);

    boolean insertBackupAppInfo(int macId,int appID);

    List<Integer> loadAppIdByMacId(int macId);

    List<Integer> loadAppIdByBoxMac(String boxMac);

    int isBackupApp(int macId,int appID);

    boolean deleteBackupApp(int macId,int appID);
}
