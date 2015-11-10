package com.changhong.client.dao;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.JodaUtils;
import com.changhong.system.domain.AppDownloadHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:16
 */
@Repository("clientDao")
public class ClientDaoImpl extends IbatisEntityObjectDao implements ClientDao {

    public List<HashMap> loadAllBoxPages() {
        List<HashMap> pages = getSqlMapClientTemplate().queryForList("Client.selectAllBoxIndexPage");
        return pages;
    }

    public List<HashMap> loadRankList(int type) {
        List<HashMap> pages = new ArrayList<HashMap>();
        if (type == 1) {
            pages = getSqlMapClientTemplate().queryForList("Client.selectNewestApps");
        } else if (type == 2) {
            pages = getSqlMapClientTemplate().queryForList("Client.selectHotestApps");
        } else {
            Map<String, Object> parameters = new HashMap<String, Object>();
            String firstDayThisMonth = JodaUtils.getFirstDateOfMonth().toString() + " 00:00:00";
            String firstDayNextMonth = JodaUtils.getFirstDateOfNextMonth().toString() + " 00:00:00";
            parameters.put("firstDayThisMonth", firstDayThisMonth);
            parameters.put("firstDayNextMonth", firstDayNextMonth);
            pages = getSqlMapClientTemplate().queryForList("Client.selectFastestApps", parameters);
        }
        return pages;
    }

    public void updateAppDownloadTime(AppDownloadHistory history) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("appId", history.getAppId());
        getSqlMapClientTemplate().update("Client.updateAppDownloadTimes", parameters);

        parameters.put("staYear", history.getYear());
        parameters.put("staMonth", history.getMonth());
        parameters.put("staDay", history.getDay());
        parameters.put("staHour", history.getHour());
        parameters.put("boxMac", history.getBoxMac());
        parameters.put("farCategoryId", history.getAppfatherCategoryId());
        parameters.put("categoryId", history.getAppCategoryId());
        getSqlMapClientTemplate().insert("Client.insertAppDownloadHistory", parameters);
    }

    public List<HashMap> loadAppDetailsRecommend(int categoryId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("categoryId", categoryId);
        return getSqlMapClientTemplate().queryForList("Client.selectHotestAppsByCategory", parameters);
    }

    public boolean insertBackupAppInfo(String boxMac, String appIDs) {
        Integer id = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        Logger log = LogManager.getLogger(ClientDaoImpl.class);

        parameters.put("box_mac",boxMac);
        parameters.put("app_ids",appIDs);

        id = (Integer)getSqlMapClientTemplate().insert("Client.insertAppBackupInfo",parameters);
        if(id != null) {
            log.info("insertBackupAppInfo    " + id.intValue());
            return true;
        }
        log.info("insertBackupAppInfo    false");

        return false;
    }

    public int updateBackupAppInfo(String boxMac, String appIDs) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("box_mac",boxMac);
        parameters.put("app_ids",appIDs);

        return getSqlMapClientTemplate().update("Client.updateAppBackupInfo",parameters);
    }

    public String loadBackupAppInfo(String boxMac) {
        return (String)getSqlMapClientTemplate().queryForObject("Client.selectAppIdByBoxMac", boxMac);
    }


}
