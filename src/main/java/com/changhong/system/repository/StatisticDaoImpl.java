package com.changhong.system.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHDateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-8-5
 * Time: 下午1:41
 */
@Repository("statisticDao")
public class StatisticDaoImpl extends HibernateEntityObjectDao implements StatisticDao {

    public JSONArray loadStatisDataForColumn(int categoryId, int year, int month) throws JSONException {
        Map<Integer, Integer> statistic = new HashMap<Integer, Integer>();
        JSONArray array = new JSONArray();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //生成种类的查询条件
        String categorySQL = "";
        if (categoryId > 0) {
            boolean equal = false;
            SQLQuery query = session.createSQLQuery("select id from app_category where parent_id is null");
            List list = query.list();
            for (Object o : list) {
                Integer parentCategoryId = (Integer) o;
                if (parentCategoryId == categoryId) {
                    equal = true;
                     break;
                }
            }

            if (equal) {
                categorySQL = " and app_father_category_id = " + categoryId;
            } else {
                categorySQL = " and app_category_id = " + categoryId;
            }
        }

        //按月统计
        if (month > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("select sta_day, count(id) as total from app_download_history " +
                    "where sta_year = " + year + " and sta_month = " + month);
            if (categoryId > 0) {
                builder.append(categorySQL);
            }
            builder.append(" group by sta_day");
            SQLQuery query = session.createSQLQuery(builder.toString());
            List list = query.list();

            int totalDays = CHDateUtils.getTotalDaysForOneMonth(year, month);
            for (int i = 1; i <= totalDays; i++) {
                statistic.put(i, 0);
            }

            for (Object o : list) {
                Object[] result = (Object[]) o;
                Integer day = (Integer) result[0];
                BigInteger total = (BigInteger) result[1];
                statistic.put(day, total.intValue());
            }

            JSONObject json = new JSONObject();
            StringBuffer buffer = new StringBuffer();
            for (int i=1; i<= totalDays; i++) {
                buffer.append(statistic.get(i) + ",");
            }
            json.put("days", CHDateUtils.getTotalDaysForOneMonth(year, month));
            json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
            array.add(json);
        } else {
            //按年统计
            StringBuilder builder = new StringBuilder();
            builder.append("select sta_month, count(id) as total from app_download_history " +
                    "where sta_year = " + year);
            if (categoryId > 0) {
                builder.append(categorySQL);
            }
            builder.append(" group by sta_month");
            SQLQuery query = session.createSQLQuery(builder.toString());
            List list = query.list();

            int totalMonths = 12;
            for (int i = 1; i <= 12; i++) {
                statistic.put(i, 0);
            }

            for (Object o : list) {
                Object[] result = (Object[]) o;
                Integer day = (Integer) result[0];
                BigInteger total = (BigInteger) result[1];
                statistic.put(day, total.intValue());
            }

            JSONObject json = new JSONObject();
            StringBuffer buffer = new StringBuffer();
            for (int i=1; i<= totalMonths; i++) {
                buffer.append(statistic.get(i) + ",");
            }
            json.put("days", totalMonths);
            json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
            array.add(json);
        }

        return array;
    }

    public JSONArray loadStatisDataForPie(int categoryId, int year, int month) throws JSONException {
        JSONArray array = new JSONArray();
        StringBuffer buffer = new StringBuffer();
        StringBuffer bufferTotal = new StringBuffer();
        JSONObject json = new JSONObject();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //生成种类的名称
        Map<String, String> categoryMapping = new HashMap<String, String>();
        SQLQuery names = session.createSQLQuery("select id, category_name from app_category");
        List nameList = names.list();
        for (Object o : nameList) {
            Object[] result = (Object[]) o;
            Integer id = (Integer) result[0];
            String name = result[1].toString();
            categoryMapping.put(String.valueOf(id), name);
        }

        //生成种类的查询条件
        String selectColumn = "";
        String categorySQL = "";
        String groupCategorySQL = "";
        if (categoryId > 0) {
            boolean equal = false;
            SQLQuery query = session.createSQLQuery("select id from app_category where parent_id is null");
            List list = query.list();
            for (Object o : list) {
                Integer parentCategoryId = (Integer) o;
                if (parentCategoryId == categoryId) {
                    equal = true;
                    break;
                }
            }

            if (equal) {
                selectColumn = " app_category_id ";
                categorySQL = " and app_father_category_id = " + categoryId;
                groupCategorySQL = " group by app_category_id";
            } else {
                selectColumn = " app_category_id ";
                categorySQL = " and app_category_id = " + categoryId;
                groupCategorySQL = " group by app_category_id";
            }
        } else {
            selectColumn = " app_father_category_id ";
            groupCategorySQL = " group by app_father_category_id";
        }

        if (month > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("select DISTINCT(" + selectColumn + "), count(id) as total from app_download_history " +
                    "where sta_year = " + year + " and sta_month = " + month);
            builder.append(categorySQL);
            builder.append(groupCategorySQL);

            SQLQuery queryCount = session.createSQLQuery(builder.toString());
            List list = queryCount.list();

            if (list != null && list.size() > 0) {
                for (Object o : list) {
                    Object[] result = (Object[]) o;
                    Integer categoryGroup = (Integer) result[0];
                    String total = result[1].toString();

                    buffer.append(categoryMapping.get(categoryGroup + "") + ",");
                    bufferTotal.append(total + ",");
                }

            }

            if (StringUtils.hasText(buffer.toString())) {
                json.put("categoryGroup", buffer.toString().substring(0, buffer.toString().length() - 1));
            } else {
                json.put("categoryGroup", "无统计数据");
            }
            if (StringUtils.hasText(bufferTotal.toString())) {
                json.put("total", bufferTotal.toString().substring(0, bufferTotal.toString().length() - 1));
            } else {
                json.put("total", "0");
            }
            array.add(json);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("select DISTINCT(" + selectColumn + "), count(id) as total from app_download_history " +
                    "where sta_year = " + year);
            builder.append(categorySQL);
            builder.append(groupCategorySQL);

            SQLQuery queryCount = session.createSQLQuery(builder.toString());
            List list = queryCount.list();

            if (list != null && list.size() > 0) {
                for (Object o : list) {
                    Object[] result = (Object[]) o;
                    Integer categoryGroup = (Integer) result[0];
                    String total = result[1].toString();

                    buffer.append(categoryMapping.get(categoryGroup + "") + ",");
                    bufferTotal.append(total + ",");
                }

            }

            if (StringUtils.hasText(buffer.toString())) {
                json.put("categoryGroup", buffer.toString().substring(0, buffer.toString().length() - 1));
            } else {
                json.put("categoryGroup", "无统计数据");
            }
            if (StringUtils.hasText(bufferTotal.toString())) {
                json.put("total", bufferTotal.toString().substring(0, bufferTotal.toString().length() - 1));
            } else {
                json.put("total", "0");
            }
            array.add(json);
        }

        return array;
    }
}
