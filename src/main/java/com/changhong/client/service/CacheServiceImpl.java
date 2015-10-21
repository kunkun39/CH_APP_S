package com.changhong.client.service;

import com.changhong.common.utils.CHPagingUtils;
import com.changhong.system.domain.*;
import com.changhong.system.repository.AppDao;
import com.changhong.system.web.facade.assember.AppCategoryWebAssember;
import com.changhong.system.web.facade.assember.AppMustWebAssember;
import com.changhong.system.web.facade.assember.LuncherRecommendWebAssember;
import com.changhong.system.web.facade.assember.MarketAppWebAssember;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import com.changhong.system.web.facade.dto.AppMustDTO;
import com.changhong.system.web.facade.dto.LuncherRecommendDTO;
import com.changhong.system.web.facade.dto.MarketAppDTO;
import com.javacodegeeks.concurrent.ConcurrentLinkedHashMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午11:17
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private final static Logger log = LogManager.getLogger(CacheServiceImpl.class);

    private static final int MAX_CACHE_SIZE = 10000;

    @Autowired
    private AppDao appDao;

    private ConcurrentLinkedHashMap<String, AppCategoryDTO> categoryCache = new ConcurrentLinkedHashMap<String, AppCategoryDTO>(200);

    private ConcurrentLinkedHashMap<String, MarketAppDTO> appCache = new ConcurrentLinkedHashMap<String, MarketAppDTO>(MAX_CACHE_SIZE);

    private ConcurrentLinkedHashMap<String, LuncherRecommendDTO> luncherCache = new ConcurrentLinkedHashMap<String, LuncherRecommendDTO>(20);

    private ConcurrentLinkedHashMap<String, AppMustDTO> mustAppCache = new ConcurrentLinkedHashMap<String, AppMustDTO>(20);

    private String bootImageFileName = "initial.png";

    public void obtainInitCachedObjects() {
        long begin = System.currentTimeMillis();

        /**
         * 缓存APP
         */
        int totalApps = appDao.loadMarketAppSize("", -1, "PASSED");
        CHPagingUtils paging = new CHPagingUtils(totalApps);
        int totalPages = paging.getNumPages();

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            paging.setCurrentPage(currentPage + "");

            List<MarketApp> apps = appDao.loadMarketApps("", -1, "PASSED", paging.getStartPosition(), paging.getMaxItems());
            for (MarketApp app : apps) {
                MarketAppDTO dto = MarketAppWebAssember.toMarketAppDTO(app);
                appCache.put("APP_" + dto.getId(), dto);
            }
        }
        log.info("finish init apps with objects count " + appCache.size());

        /**
         * 缓存CATEGORY
         */
        List<AppCategory> categories = appDao.loadAllCategory();
        List<AppCategoryDTO> dtos = AppCategoryWebAssember.toAppCategoryDTOList(categories, false);
        for (AppCategoryDTO dto : dtos) {
            categoryCache.put("CATE_" + dto.getId(), dto);
        }
        log.info("finish init category with objects count " + categoryCache.size());

         /**
         * 缓存Luncher
         */
        List<LuncherRecommend> recommends = appDao.loadAllLuncherRecommend();
        List<LuncherRecommendDTO> recommendDTOs = LuncherRecommendWebAssember.toLuncherRecommendDTOList(recommends);
        for (LuncherRecommendDTO dto : recommendDTOs) {
            luncherCache.put("LUN_" + dto.getId(), dto);
        }
        log.info("finish init luncher recommend with objects count " + luncherCache.size());

         /**
         * 强制应用
         */
        List<AppMust> appMusts = appDao.loadAllAppMust();
        List<AppMustDTO> appMustDTOs = AppMustWebAssember.toAppMustDTOList(appMusts);
        for (AppMustDTO dto : appMustDTOs) {
            mustAppCache.put("MUST_" + dto.getId(), dto);
        }
        log.info("finish init must app with objects count " + mustAppCache.size());

        /**
         * 强制应用
         */
        ClientBootImage bootImage = (ClientBootImage) appDao.findById(1, ClientBootImage.class);
        bootImageFileName = bootImage.getActualFileName();
        log.info("finish init boot image");

        long end = System.currentTimeMillis();
        long during = end - begin;
        log.info("finish init all in " + during + "ms");
    }

    public void resetAppCategoryInCache(AppCategoryDTO dto, boolean remove) {
        if (remove) {
            categoryCache.remove("CATE_" + dto.getId());
        } else {
            categoryCache.put("CATE_" + dto.getId(), dto);
        }
    }

    public void resetMarketAppInCache(MarketAppDTO dto, boolean remove) {
        if (remove) {
            appCache.remove("APP_" + dto.getId());
        } else {
            appCache.put("APP_" + dto.getId(), dto);
        }
    }

    public MarketAppDTO obtainMarketAppInCache(int appId) {
        MarketAppDTO dto = appCache.get("APP_" + appId);
        if (dto != null) {
            log.info("get app from cache for id " + appId);
            return dto;
        }

        log.info("not get app from cache for id " + appId);
        MarketApp app = (MarketApp) appDao.findById(appId, MarketApp.class);
        dto =  MarketAppWebAssember.toMarketAppDTO(app);
        appCache.put("APP_" + appId, dto);
        return dto;
    }

    public List<MarketAppDTO> obtainMarketAppInCache(String[] appPackages) {
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>(appPackages.length);

        for (String appPackage : appPackages) {
            boolean find = false;

            for (MarketAppDTO dto : appCache.values()) {
                if (dto.getAppPackage().equals(appPackage)) {
                    apps.add(dto);
                    find = true;
                    log.info("get app from cache for package " + appPackage);
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

        AppCategoryDTO get = null;
        for (AppCategoryDTO loop : categoryCache.values()) {
            int loopDategoryId = loop.getId();
            if (loopDategoryId == categoryId) {
                get = loop;
                break;
            }
        }

        if (get != null && get.getParentId() > 0) {
            idLists.add(categoryId + "");
        } else {
            for (AppCategoryDTO loop : categoryCache.values()) {
                int loopParentId = loop.getParentId();
                if (loopParentId == categoryId) {
                    idLists.add(loop.getId() + "");
                }
            }
        }

        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        for (MarketAppDTO dto : appCache.values()) {
            int loopCategoryId = dto.getCategoryId();
            if (idLists.contains(loopCategoryId + "")) {
                apps.add(dto);
            }
        }
        return apps;
    }

    public List<MarketAppDTO> obtainSearchApps(String keyWords) {
        keyWords = keyWords.toUpperCase();
        int searchNumber = 0;
        List<MarketAppDTO> apps = new ArrayList<MarketAppDTO>();
        for (MarketAppDTO dto : appCache.values()) {
            String fullName = dto.getPinYingFull();
            String shortName = dto.getPinYingShort();
            if (fullName.startsWith(keyWords) || shortName.startsWith(keyWords)) {
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
        return dto.getCategoryId();
    }

    public int obtainCategoryFatherId(int categoryId) {
        for (AppCategoryDTO dto : categoryCache.values()) {
            if (dto.getId() == categoryId) {
                return dto.getParentId();
            }
        }
        return -1;
    }

    public void updateAppDownloadTimes(int appId) {
        MarketAppDTO dto = obtainMarketAppInCache(appId);
        dto.setDownloadTimes(dto.getDownloadTimes() + 1);
        resetMarketAppInCache(dto, false);
    }

    /************************************LUNCHER推荐部分************************************/

    public void resetLuncherRecommendInCache(LuncherRecommendDTO dto, boolean remove) {
        if (remove) {
            luncherCache.remove("LUN_" + dto.getId());
        } else {
            luncherCache.put("LUN_" + dto.getId(), dto);
        }
    }

    public List<LuncherRecommendDTO> obtainLuncherRecommends() {
        return new ArrayList<LuncherRecommendDTO>(luncherCache.values());
    }

    /************************************应用强制升级和卸载************************************/

    public void resetAppMustInCache(AppMustDTO dto, boolean remove) {
        if (remove) {
            mustAppCache.remove("MUST_" + dto.getId());
        } else {
            mustAppCache.put("MUST_" + dto.getId(), dto);
        }
    }

    public List<AppMustDTO> obtainAppMust() {
        return new ArrayList<AppMustDTO>(mustAppCache.values());
    }

    /************************************开机图片************************************/

    public String getBootImageFileName() {
        return bootImageFileName;
    }

    public void setBootImageFileName(String bootImageFileName) {
        this.bootImageFileName = bootImageFileName;
    }
}
