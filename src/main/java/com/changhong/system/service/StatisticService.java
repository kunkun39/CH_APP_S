package com.changhong.system.service;

import com.alibaba.fastjson.JSONArray;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午2:05
 */
public interface StatisticService {

    JSONArray obtainStatisDataForColumn(int categoryId, int year, int month);

    JSONArray obtainStatisDataForPie(int categoryId, int year, int month);

    JSONArray obtainStaticBackUpDataForLine(int year, int month);
}
