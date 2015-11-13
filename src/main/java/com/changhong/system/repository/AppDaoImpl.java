package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:31
 */
@Repository("appDao")
public class AppDaoImpl extends HibernateEntityObjectDao implements AppDao {

    /**************************************类别部分****************************************/

    public List<AppCategory> loadAllCategory() {
        return getHibernateTemplate().find("from AppCategory");
    }

    public List<AppCategory> loadAllFirstLevelCategory() {
        return getHibernateTemplate().find("from AppCategory a where a.parent is NULL");
    }

    public int loadCategoryApps(int categoryId) {
        int size = ((Long)getHibernateTemplate().find("select count(m.id) from MarketApp m where m.appCategory.id = ?", new Object[]{categoryId}).get(0)).intValue();
        return size;
    }

    /**************************************专题部分****************************************/

    public List<AppTopic> loadAllTopics() {
        return getHibernateTemplate().find("from AppTopic");
    }

    public void removeConstraintForApp(int topicId) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        SQLQuery query = session.createSQLQuery("delete from app_topic_link where topic_id = " + topicId);
        query.executeUpdate();
    }

    /**************************************应用部分****************************************/

    public boolean loadAppPackageDuplicate(int marketAppId, String packageName) {
        int size = ((Long)getHibernateTemplate().find("select count(m.id) from MarketApp m where m.appPackage = ? and m.id != ? ", new Object[]{packageName, marketAppId}).get(0)).intValue();
        return size > 0 ? true : false;
    }

    public List<MarketApp> loadMarketApps(String appName, int categoryId, int topicId, String appStatus, int startPosition, int pageSize) {
        boolean isIn = false;
        List<Integer> ids = new ArrayList<Integer>();
        if (categoryId > 0) {
            AppCategory category = (AppCategory) getHibernateTemplate().find("from AppCategory a where a.id = " + categoryId).get(0);
            if (category.getParent() == null) {
                isIn = true;
                List<AppCategory> below = category.getAllCategoryBelow();
                for (AppCategory appCategory : below) {
                    ids.add(appCategory.getId());
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("select m from MarketApp m");
        if (topicId > 0) {
            builder.append(" left join m.appTopics a");
        }
        builder.append(" where 1=1");
        if (StringUtils.hasText(appName)) {
            String convertAppName = appName.toUpperCase();
            builder.append(" and m.appName like '%" + convertAppName + "%' or m.pinYingShort like '%" + convertAppName + "%' or m.pinYingFull like '%" + convertAppName + "%'");
        }
        if (categoryId > 0) {
            if (isIn) {
                builder.append(" and m.appCategory.id in (" + CHStringUtils.convertListToSQLIn(ids) + ")");
            } else {
                builder.append(" and m.appCategory.id = " + categoryId);
            }
        }
        if (!appStatus.equals("ALL")) {
            builder.append(" and m.appStatus = '" + appStatus + "'");
        }
        if (topicId > 0) {
            builder.append(" and a.id = " + topicId + "");
        }

        builder.append(" order by m.id desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<MarketApp> apps = query.list();
        return apps;
    }

    public int loadMarketAppSize(String appName, int categoryId, int topicId, String appStatus) {
        boolean isIn = false;
        List<Integer> ids = new ArrayList<Integer>();
        if (categoryId > 0) {
            AppCategory category = (AppCategory) getHibernateTemplate().find("from AppCategory a where a.id = " + categoryId).get(0);
            if (category.getParent() == null) {
                isIn = true;
                List<AppCategory> below = category.getAllCategoryBelow();
                for (AppCategory appCategory : below) {
                    ids.add(appCategory.getId());
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("select count(m.id) from MarketApp m");
        if (topicId > 0) {
            builder.append(" left join m.appTopics a");
        }
        builder.append(" where 1=1");
        if (StringUtils.hasText(appName)) {
            String convertAppName = appName.toUpperCase();
            builder.append(" and m.appName like '%" + convertAppName + "%' or m.pinYingShort like '%" + convertAppName +  "%' or m.pinYingFull like '%" + convertAppName + "%'");
        }
        if (categoryId > 0) {
            if (isIn) {
                builder.append(" and m.appCategory.id in (" + CHStringUtils.convertListToSQLIn(ids) + ")");
            } else {
                builder.append(" and m.appCategory.id = " + categoryId);
            }
        }
        if (!appStatus.equals("ALL")) {
            builder.append(" and m.appStatus = '" + appStatus + "'");
        }
        if (topicId > 0) {
            builder.append(" and a.id = " + topicId + "");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        return ((Long)query.list().get(0)).intValue();
    }

    public List<MarketApp> loadPageAppsByStartNumber(int startNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("from MarketApp m order by m.downloadTimes desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(20);
        query.setFirstResult(startNumber);

        List<MarketApp> apps = query.list();
        return apps;
    }

    public List<AppChangeHistory> loadAppChangeHistories(int appId) {
        return getHibernateTemplate().find("from AppChangeHistory a where a.app.id = ? order by id desc", new Object[]{appId});
    }

    /**************************************推荐部分****************************************/

    public List<MarketApp> loadRecommendApps(int categoryId, String appName) {
        List<MarketApp> apps = new ArrayList<MarketApp>();

        boolean isIn = false;
        List<Integer> ids = new ArrayList<Integer>();
        if (categoryId > 0) {
            AppCategory category = (AppCategory) getHibernateTemplate().find("from AppCategory a where a.id = " + categoryId).get(0);
            if (category.getParent() == null) {
                isIn = true;
                List<AppCategory> below = category.getAllCategoryBelow();
                for (AppCategory appCategory : below) {
                    ids.add(appCategory.getId());
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("from MarketApp m where 1=1");
        if (StringUtils.hasText(appName)) {
            String convert = appName.toUpperCase();
            builder.append(" and (m.appName like '%" + appName + "%' or m.pinYingShort like '%" + convert + "%' or m.pinYingFull like '%" + convert + "%')");
        }
        if (categoryId > 0) {
            if (isIn) {
                builder.append(" and m.appCategory.id in (" + CHStringUtils.convertListToSQLIn(ids) + ")");
            } else {
                builder.append(" and m.appCategory.id = " + categoryId);
            }
        }
        builder.append(" and m.appStatus = 'PASSED' order by m.downloadTimes desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(20);
        query.setFirstResult(0);

        apps = query.list();
        return apps;
    }

    public BoxRecommend loadBoxRecommendPosition(int pageNumber, int recommendPosition) {
        List list = getHibernateTemplate().find("from BoxRecommend b where b.pageNumber = ? and b.recommendPosition = ?", new Object[]{pageNumber, recommendPosition});
        if (list.isEmpty()) {
            return null;
        }
        return (BoxRecommend) list.get(0);
    }

    public List<BoxRecommend> loadAllBoxRecommends() {
        return getHibernateTemplate().find("from BoxRecommend");
    }

    public BoxRecommend loadCheckIsAppRecommend(int appId, int pageNumber, int recommendPosition) {
        List<BoxRecommend> recommends = getHibernateTemplate().find("from BoxRecommend b where b.marketApp.id = ? or b.tmpMarketApp.id = ?", new Object[]{appId, appId});
        if (recommends.isEmpty()) {
            return null;
        }
        for (BoxRecommend recommend : recommends) {
            if (recommend.getPageNumber() != pageNumber && recommend.getRecommendPosition() != recommendPosition) {
                return recommend;
            }
        }
        return null;
    }

    /************************************LUNCHER推荐部分************************************/

    public List<LuncherRecommend> loadAllLuncherRecommend() {
        return getHibernateTemplate().find("from LuncherRecommend l order by l.position");
    }

    public int loadMaxLuncherRecommendPosition() {
        Object o = getHibernateTemplate().find("select max(l.position) from LuncherRecommend l").get(0);
        if (o == null) {
            return 1;
        }
        return ((Integer) o) + 1;
    }

     public boolean isAppLauncherSet(int appId) {
        int size = ((Long)getHibernateTemplate().find("select count(id) from LuncherRecommend a where a.marketApp.id = ?", new Object[]{appId}).get(0)).intValue();
        return size > 0 ? true : false;
    }

    /************************************应用强制升级和卸载************************************/

    public List<AppMust> loadAllAppMust() {
        return getHibernateTemplate().find("from AppMust");
    }

    public List<AppMust> loadAppMust(boolean install) {
        return getHibernateTemplate().find("from AppMust a where a.install = ?", new Object[]{install});
    }

    public boolean isAppMustSet(int appId) {
        int size = ((Long)getHibernateTemplate().find("select count(id) from AppMust a where a.marketApp.id = ?", new Object[]{appId}).get(0)).intValue();
        return size > 0 ? true : false;
    }
}
