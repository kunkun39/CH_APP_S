package com.changhong.client.dao;

import com.changhong.client.domain.AppFast;
import com.changhong.common.domain.AppBackupHistory;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.JodaUtils;
import com.changhong.system.domain.AppDownloadHistory;
import org.springframework.stereotype.Repository;

import java.util.*;

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
            String currentYear = CHDateUtils.getCurrentYear() + "";
            String currentMonth = CHDateUtils.getCurrentMonth() + "";
            parameters.put("currentYear", currentYear);
            parameters.put("currentMonth", currentMonth);
            pages = getSqlMapClientTemplate().queryForList("Client.selectFastestApps", parameters);

            //recreate the result
            Map<String, AppFast> values = new HashMap<String, AppFast>();
            for (HashMap page : pages) {
                int appId = (Integer) page.get("app_id");
                AppFast fast = values.get(appId + "");
                if (fast == null) {
                    fast = new AppFast(appId);
                }
                fast.add();
                values.put(appId + "", fast);
            }
            Collection<AppFast> tempFasts = values.values();
            List<AppFast> fasts = new ArrayList<AppFast>();
            for (AppFast fast : tempFasts) {
                fasts.add(fast);
            }
            Collections.sort(fasts);

            pages.clear();
            for (AppFast fast : fasts) {
                Map<String, Integer> appResult = new HashMap<String, Integer>();
                appResult.put("app_id", fast.getAppId());
                pages.add((HashMap) appResult);
            }
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

    public boolean insertBackupAppHistory(AppBackupHistory appBackupHistory) {
        Object object = null;
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("sta_year",appBackupHistory.getYear());
        parameters.put("sta_month",appBackupHistory.getMonth());
        parameters.put("sta_day",appBackupHistory.getDay());
        parameters.put("sta_hour",appBackupHistory.getHour());
        parameters.put("box_mac",appBackupHistory.getBoxMac());
        parameters.put("opcode",appBackupHistory.getOpcode());
        parameters.put("app_ids",appBackupHistory.getAppIds());

        object = getSqlMapClientTemplate().insert("Client.insertAppBackupHistory",parameters);

        if(object != null) {
            return true;
        }

        return false;
    }

    public boolean insertBackupAppHistory(List<AppBackupHistory> backupHistoryList) {
        if(!CHListUtils.listIsEmpty(backupHistoryList)) {
            Object object = null;

            object = getSqlMapClientTemplate().insert("Client.insertAppBackupHistoryList",backupHistoryList);

            if(object != null) {
                return true;
            }
        }
        return false;
    }

    public List<HashMap> loadBackupAppHistoryCount(int year, int opcode) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("sta_year",year);
        parameters.put("opcode",opcode);

        return getSqlMapClientTemplate().queryForList("Client.selectAppBackupHistoryCountByMonth", parameters);
    }

    public List<HashMap> loadBackupAppHistoryCount(int year, int month, int opcode) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("sta_year",year);
        parameters.put("sta_month",month);
        parameters.put("opcode",opcode);

        return getSqlMapClientTemplate().queryForList("Client.selectAppBackupHistoryCountByDay", parameters);
    }
}
