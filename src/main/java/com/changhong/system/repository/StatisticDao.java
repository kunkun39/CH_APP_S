package com.changhong.system.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.changhong.common.repository.EntityObjectDao;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午1:41
 */
public interface StatisticDao extends EntityObjectDao {

    JSONArray loadStatisDataForColumn(int categoryId, int year, int month) throws JSONException;

    JSONArray loadStatisDataForPie(int categoryId, int year, int month) throws JSONException;
}
