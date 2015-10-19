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

    int loadClientVersion();

    List<HashMap> loadAllAppCategoryInfo();

    List<HashMap> loadAllBoxPages();

    List<HashMap> loadRankList(int type);

    void updateAppDownloadTime(AppDownloadHistory history);

    List<HashMap> loadAppDetailsRecommend(int categoryId);
}
