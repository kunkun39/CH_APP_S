package com.changhong.client.service;

import com.changhong.common.utils.*;
import com.changhong.system.domain.*;
import com.changhong.system.repository.AppDao;
import com.changhong.system.repository.SystemDao;
import com.changhong.system.web.facade.assember.*;
import com.changhong.system.web.facade.dto.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * User: Peng Jie
 */
@Service("memcacheService")
public class MemCacheServiceImpl implements CacheService, SyncCallBack {

    private final static Logger log = LogManager.getLogger(MemCacheServiceImpl.class);

    private final static String MARKET_APP = "APP_";

    private final static String CATEGORY_APP = "CATE_";

    private final static String TOPIC_APP = "TOP_";

    private final static String LAUNCHER_RECOMMEND_APP = "LUN_";

    private final static String MUST_APP = "MUST_";

    private final static String MULTIP_HOST = "HOST_";

    private final static String IMAGE_NAME = "IMG_NAME";

    private final static String APK_VERSION = "APK_VER";

    private final static String IS_UPDATE = "IS_UPDATE";

    @Autowired
    private AppDao appDao;

    @Autowired
    private SystemDao systemDao;

    @Value("${application.memcache.master}")
    private boolean isMaster;

    public void obtainInitCachedObjects() {
        long begin = System.currentTimeMillis();

        /**
         * 初始化memcache
         */
        boolean successful = CHMemcacheUtils.initMemCache();
        if (successful && isMaster) {
            updateMemCacheFromDB();
        }

        long end = System.currentTimeMillis();
        long during = end - begin;
        log.info("finish init all in " + during + "ms");
    }

    public void processDestoryCached() {
        CHMemcacheUtils.stop();
    }

    /**
     * *********************************文件服务器***********************************
     */

    public void resetMultipHost(int hostId, String hostName, boolean remove) {
        if (remove) {
            CHMemcacheUtils.remove(MULTIP_HOST + hostId);
        } else {
            CHMemcacheUtils.put(MULTIP_HOST + hostId, DesUtils.getEncString(hostName));
        }
    }

    public String getRandomMutipHost() {
        List<String> list = obtainObjects(MULTIP_HOST);
        if (!CHListUtils.listIsEmpty(list)) {
            int index = NumberUtils.generateRandomNumber(list.size());
            return list.get(index);
        }
        return null;
    }

    /**
     * *********************************专题和类别部分***********************************
     */

    public void resetAppCategoryInCache(AppCategoryDTO dto, boolean remove) {
        if (remove) {
            CHMemcacheUtils.remove(CATEGORY_APP + dto.getId());
        } else {
            CHMemcacheUtils.put(CATEGORY_APP + dto.getId(), dto);
        }
    }

    public Collection<AppCategoryDTO> obtainAllCategories() {
        List<AppCategoryDTO> list = obtainObjects(CATEGORY_APP);
        if (CHListUtils.listIsEmpty(list)) {
            list = null;
        }
        return list;
    }

    public void resetAppTopicInCache(AppTopicDTO dto, boolean remove) {
        if (remove) {
            CHMemcacheUtils.remove(TOPIC_APP + dto.getId());
        } else {
            CHMemcacheUtils.put(TOPIC_APP + dto.getId(), dto);
        }
    }

    public Collection<AppTopicDTO> obtainAllTopics() {
        List<AppTopicDTO> list = obtainObjects(TOPIC_APP);
        if (CHListUtils.listIsEmpty(list)) {
            list = null;
        }
        return list;
    }

    /**
     * *********************************App部分***********************************
     */

    public void resetMarketAppInCache(MarketAppDTO dto) {
        CHMemcacheUtils.put(MARKET_APP + dto.getId(), dto);
    }

    public MarketAppDTO obtainMarketAppInCache(int appId) {
        MarketAppDTO dto = obtainObject(MARKET_APP + appId);
        return dto;
    }

    public List<MarketAppDTO> obtainMarketAppInCache(String[] appPackages) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>(appPackages.length);
        List<MarketAppDTO> list = obtainObjects(MARKET_APP);
        for (String appPackage : appPackages) {
            boolean find = false;

            for (MarketAppDTO dto : list) {
                if (dto.getAppPackage().equals(appPackage)) {
                    apps.add(dto);
                    find = true;
                }
            }

            if (!find) {
                log.info("not get app from cache for package " + appPackage);
            }
        }
        return apps;
    }

    public List<MarketAppDTO> obtainCachedAppByCategoryId(int categoryId) {
        List<String> idLists = new ArrayList<String>();

        AppCategoryDTO get = obtainObject(CATEGORY_APP + categoryId);

        if (get != null && get.getParentId() > 0) {
            idLists.add(categoryId + "");
        } else {
            List<AppCategoryDTO> categoryAppList = obtainObjects(CATEGORY_APP);
            for (AppCategoryDTO loop : categoryAppList) {
                int loopParentId = loop.getParentId();
                if (loopParentId == categoryId) {
                    idLists.add(loop.getId() + "");
                }
            }
        }

        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        List<MarketAppDTO> marketAppList = obtainObjects(MARKET_APP);
        for (MarketAppDTO dto : marketAppList) {
            int loopCategoryId = dto.getCategoryId();
            if (idLists.contains(loopCategoryId + "") && "PASSED".equals(dto.getStatus())) {
                apps.add(dto);
            }
        }
        return apps;
    }

    public List<MarketAppDTO> obtainCachedAppByTopicId(int topicId) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        List<MarketAppDTO> list = obtainObjects(MARKET_APP);
        for (MarketAppDTO dto : list) {
            List<AppTopicDTO> appTopics = dto.getTopics();
            if (appTopics != null) {
                for (AppTopicDTO appTopic : appTopics) {
                    if (topicId == appTopic.getId() && "PASSED".equals(dto.getStatus())) {
                        apps.add(dto);
                    }
                }
            }
        }
        return apps;
    }

    public List<MarketAppDTO> obtainSearchApps(String keyWords) {
        keyWords = keyWords.toUpperCase();
        int searchNumber = 0;
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        List<MarketAppDTO> list = obtainObjects(MARKET_APP);
        for (MarketAppDTO dto : list) {
            String fullName = dto.getPinYingFull();
            String shortName = dto.getPinYingShort();
            if ((fullName.startsWith(keyWords) || shortName.startsWith(keyWords)) && "PASSED".equals(dto.getStatus())) {
                apps.add(dto);
                searchNumber++;
                if (searchNumber == 10) {
                    break;
                }
            }
        }
        return apps;
    }

    public int obtainAppCategoryId(int appId) {
        MarketAppDTO dto = obtainMarketAppInCache(appId);
        if (dto != null) {
            dto.getCategoryId();
        }
        return -1;
    }

    public int obtainCategoryFatherId(int categoryId) {
        List<AppCategoryDTO> list = obtainObjects(CATEGORY_APP);
        for (AppCategoryDTO dto : list) {
            if (dto.getId() == categoryId) {
                return dto.getParentId();
            }
        }
        return -1;
    }

    public void updateAppDownloadTimes(int appId) {
        MarketAppDTO dto = obtainMarketAppInCache(appId);
        dto.setDownloadTimes(dto.getDownloadTimes() + 1);
        resetMarketAppInCache(dto);
    }

    /**
     * *********************************LUNCHER推荐部分***********************************
     */

    public void resetLuncherRecommendInCache(LuncherRecommendDTO dto, boolean remove) {
        if (remove) {
            CHMemcacheUtils.remove(LAUNCHER_RECOMMEND_APP + dto.getId());
        } else {
            CHMemcacheUtils.put(LAUNCHER_RECOMMEND_APP + dto.getId(), dto);
        }
    }

    public List<LuncherRecommendDTO> obtainLuncherRecommends() {
        List<LuncherRecommendDTO> list = obtainObjects(LAUNCHER_RECOMMEND_APP);
        if (CHListUtils.listIsEmpty(list)) {
            list = null;
        }
        return list;
    }

    /**
     * *********************************应用强制升级和卸载***********************************
     */

    public void resetAppMustInCache(AppMustDTO dto, boolean remove) {
        if (remove) {
            CHMemcacheUtils.remove(MUST_APP + dto.getId());
        } else {
            CHMemcacheUtils.put(MUST_APP + dto.getId(), dto);
        }
    }

    public List<AppMustDTO> obtainAppMust() {
        List<AppMustDTO> list = obtainObjects(MUST_APP);
        if (CHListUtils.listIsEmpty(list)) {
            list = null;
        }
        return list;
    }

    /**
     * *********************************开机图片***********************************
     */

    public String getBootImageFileName() {
        String imgPathName = obtainObject(IMAGE_NAME);
        if (!StringUtils.hasText(imgPathName)) {
            imgPathName = ((ClientBootImage) appDao.findById(1, ClientBootImage.class)).getActualFileName();
            CHMemcacheUtils.put(IMAGE_NAME, imgPathName);
        }
        return imgPathName;
    }

    public void setBootImageFileName(String bootImageFileName) {
        CHMemcacheUtils.put(IMAGE_NAME, bootImageFileName);
    }

    /**
     * *********************************系统版本***********************************
     */

    public int getCurrentClientVersion() {
        Integer version = obtainObject(APK_VERSION);
        if (null == version) {
            ClientVersion clientVersion = systemDao.findClientVersion();
            version = clientVersion.getClientVersion();
            CHMemcacheUtils.put(APK_VERSION, version);
        }
        return version;
    }

    public void setCurrentClientVersion(int clientVersion) {
        CHMemcacheUtils.put(APK_VERSION, clientVersion);
    }

    public boolean isClientBeginUpdate() {
        Boolean clientBeginUpdate = obtainObject(IS_UPDATE);
        if (null == clientBeginUpdate) {
            ClientVersion clientVersion = systemDao.findClientVersion();
            clientBeginUpdate = clientVersion.isBeginUpdate();
            CHMemcacheUtils.put(IS_UPDATE, clientBeginUpdate);
        }
        return clientBeginUpdate;
    }

    public void setClientBeginUpdate(boolean clientBeginUpdate) {
        CHMemcacheUtils.put(IS_UPDATE, clientBeginUpdate);
    }

    /**
     * *********************************数据同步***********************************
     */

    protected <T> T obtainObject(String searchKey) {
        return (T) CHMemcacheUtils.get(searchKey);
    }

    protected <T> List<T> obtainObjects(String searchKey) {
        ArrayList<T> list = new ArrayList<T>();
        Set<String> keySet = CHMemcacheUtils.keySet();
        for (String key : keySet) {
            if (key.contains(searchKey)) {
                T object = (T) CHMemcacheUtils.get(key);
                if (object != null) {
                    list.add(object);
                }
            }
        }
        return list;
    }

    public void sync() {
        if (isMaster) {
            updateMemCacheFromDB();
        }
    }

    private void updateMemCacheFromDB() {
        log.info("updateMemCacheFromDB start!");
        /**
         * 清除memcached数据
         */
        CHMemcacheUtils.clear();

        /**
         * 缓存APP
         */
        int totalApps = appDao.loadMarketAppSize("", -1, -1, "ALL");
        CHPagingUtils paging = new CHPagingUtils(totalApps);
        int totalPages = paging.getNumPages();
        int count = 0;

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            paging.setCurrentPage(currentPage + "");

            List<MarketApp> apps = appDao.loadMarketApps("", -1, -1, "ALL", paging.getStartPosition(), paging.getMaxItems());
            for (MarketApp app : apps) {
                MarketAppDTO dto = MarketAppWebAssember.toMarketAppDTO(app);
                CHMemcacheUtils.put(MARKET_APP + dto.getId(), dto);
            }
            count += apps.size();
        }
        log.info("finish init apps with objects count " + count);

        /**
         * 缓存CATEGORY
         */
        List<AppCategory> categories = appDao.loadAllCategory();
        List<AppCategoryDTO> dtos = AppCategoryWebAssember.toAppCategoryDTOList(categories, false);
        for (AppCategoryDTO dto : dtos) {
            CHMemcacheUtils.put(CATEGORY_APP + dto.getId(), dto);
        }
        log.info("finish init category with objects count " + dtos.size());

        /**
         * 缓存TOPIC
         */
        List<AppTopic> topics = appDao.loadAllTopics();
        List<AppTopicDTO> topicDTOs = AppTopicWebAssember.toAppTopicDTOList(topics);
        for (AppTopicDTO dto : topicDTOs) {
            CHMemcacheUtils.put(TOPIC_APP + dto.getId(), dto);
        }
        log.info("finish init topic with objects count " + topicDTOs.size());

        /**
         * 缓存Luncher
         */
        List<LuncherRecommend> recommends = appDao.loadAllLuncherRecommend();
        List<LuncherRecommendDTO> recommendDTOs = LuncherRecommendWebAssember.toLuncherRecommendDTOList(recommends);
        for (LuncherRecommendDTO dto : recommendDTOs) {
            CHMemcacheUtils.put(LAUNCHER_RECOMMEND_APP + dto.getId(), dto);
        }
        log.info("finish init luncher recommend with objects count " + recommendDTOs.size());

        /**
         * 强制应用
         */
        List<AppMust> appMusts = appDao.loadAllAppMust();
        List<AppMustDTO> appMustDTOs = AppMustWebAssember.toAppMustDTOList(appMusts);
        for (AppMustDTO dto : appMustDTOs) {
            CHMemcacheUtils.put(MUST_APP + dto.getId(), dto);
        }
        log.info("finish init must app with objects count " + appMustDTOs.size());

        /**
         * 强制应用
         */
        ClientBootImage bootImage = (ClientBootImage) appDao.findById(1, ClientBootImage.class);
        CHMemcacheUtils.put(IMAGE_NAME, bootImage.getActualFileName());
        log.info("finish init boot image");

        /**
         * 系统当前版本
         */
        ClientVersion clientVersion = systemDao.findClientVersion();
        CHMemcacheUtils.put(APK_VERSION, clientVersion.getClientVersion());
        CHMemcacheUtils.put(IS_UPDATE, clientVersion.isBeginUpdate());
        log.info("finish init client version");

        /**
         * 所有的HOST
         */
        List<MultipHost> hosts = systemDao.loadAllMultipHosts();
        for (MultipHost host : hosts) {
            CHMemcacheUtils.put(MULTIP_HOST + host.getId(), DesUtils.getEncString(host.getHostName()));
        }
        log.info("finish init multip host");

        log.info("updateMemCacheFromDB end!");
    }
}
