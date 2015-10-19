package com.changhong.system.service;

import com.alibaba.fastjson.JSONArray;
import com.changhong.system.repository.StatisticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午2:05
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    public JSONArray obtainStatisDataForColumn(int categoryId, int year, int month) {
        return statisticDao.loadStatisDataForColumn(categoryId, year, month);
    }

    public JSONArray obtainStatisDataForPie(int categoryId, int year, int month) {
        return statisticDao.loadStatisDataForPie(categoryId, year, month);
    }
}
