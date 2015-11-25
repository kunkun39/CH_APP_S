package com.changhong.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.dao.ClientDao;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.repository.StatisticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午2:05
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Autowired
    private ClientDao clientDao;

    public JSONArray obtainStatisDataForColumn(int categoryId, int year, int month) {
        return statisticDao.loadStatisDataForColumn(categoryId, year, month);
    }

    public JSONArray obtainStatisDataForPie(int categoryId, int year, int month) {
        return statisticDao.loadStatisDataForPie(categoryId, year, month);
    }

    public JSONArray obtainStaticBackUpDataForLine(int year, int month) {
        Map<Integer, Integer> statisticBackUpCount = new HashMap<Integer, Integer>();
        Map<Integer, Integer> statisticDeleteCount = new HashMap<Integer, Integer>();
        JSONArray array = new JSONArray();
        List<HashMap> backupList;
        List<HashMap> deleteList;
        StringBuilder backUpbuilder = new StringBuilder();
        StringBuilder deleteUpbuilder = new StringBuilder();

        //按月统计
        if (month > 0) {

            // 统计备份
            backupList = clientDao.loadBackupAppHistoryCount(year, month , 0);

            int totalDays = CHDateUtils.getTotalDaysForOneMonth(year, month);
            for (int i = 1; i <= totalDays; i++) {
                statisticBackUpCount.put(i, 0);
            }

            for(HashMap hashMap : backupList) {
                statisticBackUpCount.put((Integer)hashMap.get("sta_day"), ((Long)hashMap.get("total")).intValue());
            }

            for (int i=1; i<= totalDays; i++) {
                backUpbuilder.append(statisticBackUpCount.get(i) + ",");
            }

            // 统计删除
            deleteList = clientDao.loadBackupAppHistoryCount(year, month , 1);
            for (int i = 1; i <= totalDays; i++) {
                statisticDeleteCount.put(i, 0);
            }

            for(HashMap hashMap : deleteList) {
                statisticDeleteCount.put((Integer)hashMap.get("sta_day"), ((Long)hashMap.get("total")).intValue());
            }

            for (int i=1; i<= totalDays; i++) {
                deleteUpbuilder.append(statisticDeleteCount.get(i) + ",");
            }

            JSONObject json = new JSONObject();
            json.put("days", totalDays);
            json.put("backup_sta", backUpbuilder.deleteCharAt(backUpbuilder.length() - 1).toString());
            json.put("delete_sta", deleteUpbuilder.deleteCharAt(deleteUpbuilder.length() - 1).toString());

            array.add(json);
        }
        else {
            // 统计备份
            backupList = clientDao.loadBackupAppHistoryCount(year, 0);

            for (int i = 1; i <= 12; i++) {
                statisticBackUpCount.put(i, 0);
            }

            for(HashMap hashMap : backupList) {
                statisticBackUpCount.put((Integer)hashMap.get("sta_month"), ((Long)hashMap.get("total")).intValue());
            }

            for (int i=1; i<= 12; i++) {
                backUpbuilder.append(statisticBackUpCount.get(i) + ",");
            }

            // 统计删除
            deleteList = clientDao.loadBackupAppHistoryCount(year, 1);

            for (int i = 1; i <= 12; i++) {
                statisticDeleteCount.put(i, 0);
            }

            for(HashMap hashMap : deleteList) {
                statisticDeleteCount.put((Integer)hashMap.get("sta_month"), ((Long)hashMap.get("total")).intValue());
            }

            for (int i=1; i<= 12; i++) {
                deleteUpbuilder.append(statisticDeleteCount.get(i) + ",");
            }

            JSONObject json = new JSONObject();
            json.put("days", 12);
            json.put("backup_sta", backUpbuilder.deleteCharAt(backUpbuilder.length() - 1).toString());
            json.put("delete_sta", deleteUpbuilder.deleteCharAt(deleteUpbuilder.length() - 1).toString());
            array.add(json);
        }
        return array;
    }
}
