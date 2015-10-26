package com.changhong.client.dao;

import com.changhong.system.domain.AppDownloadHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:16
 */
@Repository("clientDao")
public class ClientDaoImpl extends IbatisEntityObjectDao implements ClientDao {

    public String loadClientBootImage() {
        List<HashMap> bootImage = getSqlMapClientTemplate().queryForList("Client.selectClientBootImage");
        return (String)bootImage.get(0).get("actual_filename");
    }

    public int loadClientVersion() {
        List<HashMap> versions = getSqlMapClientTemplate().queryForList("Client.selectClientVersion");
        if (versions == null || versions.isEmpty()) {
            return 1;
        }
        return (Integer)versions.get(0).get("client_version");
    }

    public List<HashMap> loadAllAppCategoryInfo() {
        List<HashMap> categoryies = getSqlMapClientTemplate().queryForList("Client.selectAllAppCategory");
        return categoryies;
    }

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
            pages = getSqlMapClientTemplate().queryForList("Client.selectFastestApps");
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

    public int insertBoxMacInfo(String boxMac) {
        Integer id = null;
        Object object = null;

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("box_mac",boxMac);

        id = (Integer)getSqlMapClientTemplate().insert("Client.insertBoxMac",parameters);

        if(id != null) {
            return id.intValue();
        }
        return -1;
    }

    public int loadMacIdByBoxMac(String boxMac) {
        Integer macId = null;

        macId =  (Integer)getSqlMapClientTemplate().queryForObject("Client.selectMacIdByBoxMac",boxMac);
        if(macId != null) {
            return ((Integer)macId).intValue();
        }
        return -1;
    }

    public boolean insertBackupAppInfo(int macId,int appID) {
        Object object = null;
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("mac_id",macId);
        parameters.put("app_id",appID);

        object = getSqlMapClientTemplate().insert("Client.insertBackupInfo",parameters);
        if(object != null) {
            return true;
        }
        return false;
    }

    public List<Integer> loadAppIdByMacId(int macId) {
        List<Integer> appId = null;

        appId = getSqlMapClientTemplate().queryForList("Client.selectAppIdByMacId",macId);

        return appId;
    }

    public List<Integer> loadAppIdByBoxMac(String boxMac) {
        List<Integer> appId = null;

        appId = getSqlMapClientTemplate().queryForList("Client.selectAppIdByBoxMac",boxMac);

        return appId;
    }

    public int isBackupApp(int macId,int appID) {
        Integer isBackup = null;
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("mac_id",macId);
        parameters.put("app_id",appID);

        isBackup =  (Integer)getSqlMapClientTemplate().queryForObject("Client.selectBackupInfo",parameters);
        if(isBackup != null) {
            return 1;
        }
        return 0;
    }

    public boolean deleteBackupApp(int macId,int appID) {
        int rowNum = 0;
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("mac_id",macId);
        parameters.put("app_id",appID);

        rowNum = getSqlMapClientTemplate().delete("Client.deleteBackupApp",parameters);
        if(rowNum > 0) {
            return true;
        }
        return false;
    }
}
