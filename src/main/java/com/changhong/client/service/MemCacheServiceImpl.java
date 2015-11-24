package com.changhong.client.service;

import IceUtilInternal.StringUtil;
import com.changhong.common.utils.CHMemcacheUtils;
import com.changhong.common.utils.CHPagingUtils;
import com.changhong.common.utils.DesUtils;
import com.changhong.common.utils.NumberUtils;
import com.changhong.system.domain.*;
import com.changhong.system.repository.AppDao;
import com.changhong.system.repository.SystemDao;
import com.changhong.system.web.facade.assember.*;
import com.changhong.system.web.facade.dto.*;
import com.javacodegeeks.concurrent.ConcurrentLinkedHashMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * User: Peng Jie
 */
@Service("memcacheService")
public class MemCacheServiceImpl implements CacheService, CHCallBack {

    private final static Logger log = LogManager.getLogger(MemCacheServiceImpl.class);

    private final static String MEMCACHE_INIT_KEY = "INIT";

    private final static String MARKET_APP = "APP_";

    private final static String CATEGORY_APP = "CATE_";

    private final static String TOPIC_APP = "TOP_";

    private final static String LAUNCHER_RECOMMEND_APP = "LUN_";

    private final static String MUST_APP = "MUST_";

    private final static String MULTIP_HOST = "HOST_";

    private final static String IMAGE_NAME = "IMG_NAME";

    private final static String APK_VERSION = "APK_VER";

    private final static String IS_UPDATE = "IS_UPDATE";

    private final static int MAX_CACHE_SIZE = 10000;

    @Autowired
    private AppDao appDao;

    @Autowired
    private SystemDao systemDao;

    private ConcurrentSkipListSet<Integer> categoryIndex = new ConcurrentSkipListSet<Integer>();

    private ConcurrentSkipListSet<Integer> topicIndex = new ConcurrentSkipListSet<Integer>();

    private ConcurrentSkipListSet<Integer> launcherIndex = new ConcurrentSkipListSet<Integer>();

    private ConcurrentSkipListSet<Integer> mustIndex = new ConcurrentSkipListSet<Integer>();

    private ConcurrentSkipListSet<Integer> hostIndex = new ConcurrentSkipListSet<Integer>();

    private ConcurrentLinkedHashMap<String, Integer> appIndex = new ConcurrentLinkedHashMap<String, Integer>(MAX_CACHE_SIZE);

    private CHMemcacheUtils memcacheUtils;

    private boolean isInitEnd = false;

    public void obtainInitCachedObjects() {
        long begin = System.currentTimeMillis();

        /**
         * 初始化memcache
         */
        memcacheUtils = new CHMemcacheUtils(this);
        memcacheUtils.initMemCache();

        int sleepCounter = 0;
        boolean needReadDatafromDB = true;
        log.info("containsKey" + memcacheUtils.containsKey(MEMCACHE_INIT_KEY));
        if (memcacheUtils.containsKey(MEMCACHE_INIT_KEY)) {
            while (((String) memcacheUtils.getImmediate(MEMCACHE_INIT_KEY)).equals("init start") && sleepCounter < 10) {
                sleepCounter++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("init" + (String) memcacheUtils.getImmediate(MEMCACHE_INIT_KEY));
            if (((String) memcacheUtils.getImmediate(MEMCACHE_INIT_KEY)).equals("init end")) {
                needReadDatafromDB = false;
                updateLocalCache();
            }
        }
        if (needReadDatafromDB) {
            memcacheUtils.put(MEMCACHE_INIT_KEY, "init start");
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
                    memcacheUtils.put(MARKET_APP + dto.getId(), dto);
                    appIndex.put(dto.getAppPackage(), dto.getId());
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
                memcacheUtils.put(CATEGORY_APP + dto.getId(), dto);
                categoryIndex.add(dto.getId());
            }
            log.info("finish init category with objects count " + dtos.size());

            /**
             * 缓存TOPIC
             */
            List<AppTopic> topics = appDao.loadAllTopics();
            List<AppTopicDTO> topicDTOs = AppTopicWebAssember.toAppTopicDTOList(topics);
            for (AppTopicDTO dto : topicDTOs) {
                memcacheUtils.put(TOPIC_APP + dto.getId(), dto);
                topicIndex.add(dto.getId());
            }
            log.info("finish init topic with objects count " + topicDTOs.size());

            /**
             * 缓存Luncher
             */
            List<LuncherRecommend> recommends = appDao.loadAllLuncherRecommend();
            List<LuncherRecommendDTO> recommendDTOs = LuncherRecommendWebAssember.toLuncherRecommendDTOList(recommends);
            for (LuncherRecommendDTO dto : recommendDTOs) {
                memcacheUtils.put(LAUNCHER_RECOMMEND_APP + dto.getId(), dto);
                launcherIndex.add(dto.getId());
            }
            log.info("finish init luncher recommend with objects count " + recommendDTOs.size());

            /**
             * 强制应用
             */
            List<AppMust> appMusts = appDao.loadAllAppMust();
            List<AppMustDTO> appMustDTOs = AppMustWebAssember.toAppMustDTOList(appMusts);
            for (AppMustDTO dto : appMustDTOs) {
                memcacheUtils.put(MUST_APP + dto.getId(), dto);
                mustIndex.add(dto.getId());
            }
            log.info("finish init must app with objects count " + appMustDTOs.size());

            /**
             * 强制应用
             */
            ClientBootImage bootImage = (ClientBootImage) appDao.findById(1, ClientBootImage.class);
            memcacheUtils.put(IMAGE_NAME, bootImage.getActualFileName());
            log.info("finish init boot image");

            /**
             * 系统当前版本
             */
            ClientVersion clientVersion = systemDao.findClientVersion();
            memcacheUtils.put(APK_VERSION, clientVersion.getClientVersion());
            memcacheUtils.put(IS_UPDATE, clientVersion.isBeginUpdate());
            log.info("finish init client version");

            /**
             * 所有的HOST
             */
            List<MultipHost> hosts = systemDao.loadAllMultipHosts();
            for (MultipHost host : hosts) {
                memcacheUtils.put(MULTIP_HOST + host.getId(), DesUtils.getEncString(host.getHostName()));
                hostIndex.add(host.getId());
            }
            log.info("finish init multip host");

            memcacheUtils.put(MEMCACHE_INIT_KEY, "init end");
        }

        isInitEnd = true;
        long end = System.currentTimeMillis();
        long during = end - begin;
        log.info("finish init all in " + during + "ms");
    }

    public void processDestoryCached() {
        memcacheUtils.stop();
    }

    /**
     * *********************************文件服务器***********************************
     */

    public void resetMultipHost(int hostId, String hostName, boolean remove) {
        if (remove) {
            memcacheUtils.remove(MULTIP_HOST + hostId);
            hostIndex.remove(hostId);
        } else {
            memcacheUtils.put(MULTIP_HOST + hostId, DesUtils.getEncString(hostName));
            hostIndex.add(hostId);
        }
    }

    public String getRandomMutipHost() {
        if (!hostIndex.isEmpty()) {
            Iterator iterator = hostIndex.iterator();
            List<Integer> keys = new ArrayList<Integer>();
            while(iterator.hasNext()) {
                keys.add((Integer) iterator.next());
            }
            int index = NumberUtils.generateRandomNumber(keys.size());
            String hostName = obtainMultipHostName(index);
            if(!StringUtils.hasText(hostName)) {
                hostIndex.remove(index);
            }
        }
        return null;
    }

    /**
     * *********************************专题和类别部分***********************************
     */

    public void resetAppCategoryInCache(AppCategoryDTO dto, boolean remove) {
        if (remove) {
            memcacheUtils.remove(CATEGORY_APP + dto.getId());
            categoryIndex.remove(dto.getId());
        } else {
            memcacheUtils.put(CATEGORY_APP + dto.getId(), dto);
            categoryIndex.add(dto.getId());
        }
    }

    public Collection<AppCategoryDTO> obtainAllCategories() {
        return obtainAllCategorieApps();
    }

    public void resetAppTopicInCache(AppTopicDTO dto, boolean remove) {
        if (remove) {
            memcacheUtils.remove(TOPIC_APP + dto.getId());
            topicIndex.remove(dto.getId());
        } else {
            memcacheUtils.put(TOPIC_APP + dto.getId(), dto);
            topicIndex.add(dto.getId());
        }
    }

    public Collection<AppTopicDTO> obtainAllTopics() {
        return obtainAllTopicApps();
    }

    /**
     * *********************************App部分***********************************
     */

    public void resetMarketAppInCache(MarketAppDTO dto) {
        memcacheUtils.put(MARKET_APP + dto.getId(), dto);
        appIndex.put(dto.getAppPackage(), dto.getId());
    }

    public MarketAppDTO obtainMarketAppInCache(int appId) {
        return obtainMarketApp(appId);
    }

    public List<MarketAppDTO> obtainMarketAppInCache(String[] appPackages) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>(appPackages.length);

        for (String appPackage : appPackages) {
            boolean find = false;

            if (appIndex.containsKey(appPackage)) {
                int appId = appIndex.get(appPackage);
                MarketAppDTO dto = obtainMarketApp(appId);
                if (dto != null) {
                    apps.add(dto);
                    find = true;
                } else {
                    appIndex.remove(appPackage);
                }

                if (!find) {
                    log.info("not get app from cache for package " + appPackage);
                }
            }
        }

        return apps;
    }

    public List<MarketAppDTO> obtainCachedAppByCategoryId(int categoryId) {
        List<String> idLists = new ArrayList<String>();

        AppCategoryDTO get = obtainCategoryAPP(categoryId);

        if (get != null && get.getParentId() > 0) {
            idLists.add(categoryId + "");
        } else {
            for (AppCategoryDTO loop : obtainAllCategories()) {
                int loopParentId = loop.getParentId();
                if (loopParentId == categoryId) {
                    idLists.add(loop.getId() + "");
                }
            }
        }

        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        for (MarketAppDTO dto : obtainAllMarketApps()) {
            int loopCategoryId = dto.getCategoryId();
            if (idLists.contains(loopCategoryId + "") && "PASSED".equals(dto.getStatus())) {
                apps.add(dto);
            }
        }
        return apps;
    }

    public List<MarketAppDTO> obtainCachedAppByTopicId(int topicId) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();

        for (MarketAppDTO dto : obtainAllMarketApps()) {
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
        for (MarketAppDTO dto : obtainAllMarketApps()) {
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
        for (AppCategoryDTO dto : obtainAllCategories()) {
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
            memcacheUtils.remove(LAUNCHER_RECOMMEND_APP + dto.getId());
            launcherIndex.remove(dto.getId());
        } else {
            memcacheUtils.put(LAUNCHER_RECOMMEND_APP + dto.getId(), dto);
            launcherIndex.add(dto.getId());
        }
    }

    public List<LuncherRecommendDTO> obtainLuncherRecommends() {
        return obtainAllLuncherRecommend();
    }

    /**
     * *********************************应用强制升级和卸载***********************************
     */

    public void resetAppMustInCache(AppMustDTO dto, boolean remove) {
        if (remove) {
            memcacheUtils.remove(MUST_APP + dto.getId());
        } else {
            memcacheUtils.put(MUST_APP + dto.getId(), dto);
        }
    }

    public List<AppMustDTO> obtainAppMust() {
        return obtainAllMustApps();
    }

    /**
     * *********************************开机图片***********************************
     */

    public String getBootImageFileName() {
        String imgPathName = (String) memcacheUtils.get(IMAGE_NAME);
        if (!StringUtils.hasText(imgPathName)) {
            imgPathName = ((ClientBootImage) appDao.findById(1, ClientBootImage.class)).getActualFileName();
            memcacheUtils.put(IMAGE_NAME, imgPathName);
        }
        return imgPathName;
    }

    public void setBootImageFileName(String bootImageFileName) {
        memcacheUtils.put(IMAGE_NAME, bootImageFileName);
    }

    /**
     * *********************************系统版本***********************************
     */

    public int getCurrentClientVersion() {
        Integer version = (Integer) memcacheUtils.get(APK_VERSION);
        if (null == version) {
            ClientVersion clientVersion = systemDao.findClientVersion();
            version = clientVersion.getClientVersion();
            memcacheUtils.put(APK_VERSION, version);
        }
        return version;
    }

    public void setCurrentClientVersion(int clientVersion) {
        memcacheUtils.put(APK_VERSION, clientVersion);
    }

    public boolean isClientBeginUpdate() {
        Boolean clientBeginUpdate = (Boolean) memcacheUtils.get(IS_UPDATE);
        if (null == clientBeginUpdate) {
            ClientVersion clientVersion = systemDao.findClientVersion();
            clientBeginUpdate = clientVersion.isBeginUpdate();
            memcacheUtils.put(IS_UPDATE, clientBeginUpdate);
        }
        return clientBeginUpdate;
    }

    public void setClientBeginUpdate(boolean clientBeginUpdate) {
        memcacheUtils.put(IS_UPDATE, clientBeginUpdate);
    }

    public void onCallBack() {
        log.info("onCallBack");
        if (isInitEnd) {
            String initValue = (String) memcacheUtils.getImmediate(MEMCACHE_INIT_KEY);
            if (!StringUtils.hasText(initValue)) {
                return;
            }
            if (!initValue.equals("init start")) {
                updateLocalCache();
                //保证数据永远不会超期
                memcacheUtils.put(MEMCACHE_INIT_KEY, "init end");
            }
        }
    }

    protected AppCategoryDTO obtainCategoryAPP(int categoryId) {
        if (!categoryIndex.contains(categoryId)) {
            return null;
        }

        AppCategoryDTO dto = (AppCategoryDTO) memcacheUtils.get(CATEGORY_APP + categoryId);
        if (null == dto) {
            return dto;
        }

        log.info("not get CategoryAPP from cache for id " + categoryId);

        AppCategory category = (AppCategory) appDao.findById(categoryId, AppCategory.class);
        if (category != null) {
            dto = AppCategoryWebAssember.toAppCategoryDTO(category, false);
            memcacheUtils.put(CATEGORY_APP + categoryId, dto);
        }
        return dto;
    }

    protected AppTopicDTO obtainTopicAPP(int topicId) {
        if (!topicIndex.contains(topicId)) {
            return null;
        }

        AppTopicDTO dto = (AppTopicDTO) memcacheUtils.get(TOPIC_APP + topicId);
        if (null == dto) {
            return dto;
        }
        log.info("not get TopicAPP from cache for id " + topicId);

        AppTopic topic = (AppTopic) appDao.findById(topicId, AppTopic.class);
        if (topic != null) {
            dto = AppTopicWebAssember.toAppTopicDTO(topic);
            memcacheUtils.put(TOPIC_APP + dto.getId(), dto);
        }
        return dto;
    }

    protected LuncherRecommendDTO obtainLuncherRecommend(int recommendId) {
        if (!launcherIndex.contains(recommendId)) {
            return null;
        }

        LuncherRecommendDTO dto = (LuncherRecommendDTO) memcacheUtils.get(LAUNCHER_RECOMMEND_APP + recommendId);
        if (dto != null) {
            return dto;
        }

        log.info("not get LuncherRecommend from cache for id " + recommendId);

        LuncherRecommend recommend = (LuncherRecommend) appDao.findById(recommendId, LuncherRecommend.class);
        if (recommend != null) {
            dto = LuncherRecommendWebAssember.toLuncherRecommendDTO(recommend);
            memcacheUtils.put(LAUNCHER_RECOMMEND_APP + dto.getId(), dto);
        }

        return dto;
    }

    protected MarketAppDTO obtainMarketApp(int appId) {
        MarketAppDTO dto = (MarketAppDTO) memcacheUtils.get(MARKET_APP + appId);
        if (dto != null) {
            return dto;
        }

        log.info("not get MarketApp from cache for id " + appId);
        MarketApp app = (MarketApp) appDao.findById(appId, MarketApp.class);
        if (app != null) {
            dto = MarketAppWebAssember.toMarketAppDTO(app);
            memcacheUtils.put(MARKET_APP + appId, dto);
            appIndex.put(dto.getAppPackage(), dto.getId());
        }
        return dto;
    }

    protected AppMustDTO obtainMustApp(int mustId) {
        if (!mustIndex.contains(mustId)) {
            return null;
        }

        AppMustDTO dto = (AppMustDTO) memcacheUtils.get(MUST_APP + mustId);
        if (dto != null) {
            return dto;
        }

        log.info("not get MustApp from cache for id " + mustId);

        AppMust appMust = (AppMust) appDao.findById(mustId, AppMust.class);
        if (appMust != null) {
            dto = AppMustWebAssember.toAppMustDTODTO(appMust);
            memcacheUtils.put(MUST_APP + mustId, dto);
        }

        return dto;
    }

    protected String obtainMultipHostName(int indexId) {
        if (!hostIndex.contains(indexId)) {
            return null;
        }

        String hostName = (String) memcacheUtils.get(MULTIP_HOST);

        if(StringUtils.hasText(hostName)) {
            return hostName;
        }

        MultipHost host = (MultipHost) systemDao.findById(indexId, MultipHost.class);
        if(host != null) {
            hostName = host.getHostName();
            memcacheUtils.put(MULTIP_HOST, hostName);
        }

        return hostName;
    }

    protected List<AppCategoryDTO> obtainAllCategorieApps() {
        ArrayList<AppCategoryDTO> list = new ArrayList<AppCategoryDTO>();

        Iterator iterator = categoryIndex.iterator();
        while (iterator.hasNext()) {
            int categoryId = (Integer) iterator.next();
            AppCategoryDTO dto = obtainCategoryAPP(categoryId);
            if (dto != null) {
                list.add(dto);
            } else {
                categoryIndex.remove(categoryId);
            }
        }

        return list;
    }

    protected Collection<AppTopicDTO> obtainAllTopicApps() {
        ArrayList<AppTopicDTO> list = new ArrayList<AppTopicDTO>();

        Iterator iterator = topicIndex.iterator();
        while (iterator.hasNext()) {
            int topicId = (Integer) iterator.next();
            AppTopicDTO dto = obtainTopicAPP(topicId);
            if (dto != null) {
                list.add(dto);
            } else {
                topicIndex.remove(topicId);
            }
        }

        return list;
    }

    protected List<LuncherRecommendDTO> obtainAllLuncherRecommend() {
        ArrayList<LuncherRecommendDTO> list = new ArrayList<LuncherRecommendDTO>();
        Iterator iterator = launcherIndex.iterator();
        while (iterator.hasNext()) {
            int recommendId = (Integer) iterator.next();
            LuncherRecommendDTO dto = obtainLuncherRecommend(recommendId);
            if (dto != null) {
                list.add(dto);
            } else {
                launcherIndex.remove(recommendId);
            }
        }

        return list;
    }

    protected List<AppMustDTO> obtainAllMustApps() {
        ArrayList<AppMustDTO> list = new ArrayList<AppMustDTO>();
        Iterator iterator = mustIndex.iterator();
        while (iterator.hasNext()) {
            int mustId = (Integer) iterator.next();
            AppMustDTO dto = obtainMustApp(mustId);
            if (dto != null) {
                list.add(dto);
            } else {
                mustIndex.remove(mustId);
            }
        }

        return list;
    }

    protected List<MarketAppDTO> obtainAllMarketApps() {
        ArrayList<MarketAppDTO> list = new ArrayList<MarketAppDTO>();

        for (int appId : appIndex.values()) {
            MarketAppDTO dto = obtainMarketApp(appId);
            if (dto != null) {
                list.add(dto);
            }
        }
        return list;
    }

    protected void updateLocalCache() {
        log.info("updateLocalCache start!");
        Set<String> keySet = memcacheUtils.keySet();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String keyName = (String) iterator.next();
            if (keyName.contains(MARKET_APP)) {
                MarketAppDTO dto = (MarketAppDTO) memcacheUtils.get(keyName);
                if (dto != null) {
                    appIndex.put(dto.getAppPackage(), dto.getId());
                }
            } else if (keyName.contains(CATEGORY_APP)) {
                categoryIndex.add(Integer.valueOf(keyName.substring(CATEGORY_APP.length())));
            } else if (keyName.contains(TOPIC_APP)) {
                topicIndex.add(Integer.valueOf(keyName.substring(TOPIC_APP.length())));
            } else if (keyName.contains(LAUNCHER_RECOMMEND_APP)) {
                launcherIndex.add(Integer.valueOf(keyName.substring(LAUNCHER_RECOMMEND_APP.length())));
            } else if (keyName.contains(MUST_APP)) {
                mustIndex.add(Integer.valueOf(keyName.substring(MUST_APP.length())));
            }
            else if (keyName.contains(MULTIP_HOST)) {
                hostIndex.add(Integer.valueOf(keyName.substring(MULTIP_HOST.length())));
            }
        }
        log.info("updateLocalCache end!");
    }
}
