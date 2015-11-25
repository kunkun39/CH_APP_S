package com.changhong.system.service;

import com.changhong.common.exception.CHAppExistException;
import com.changhong.common.exception.CHDocumentOperationException;
import com.changhong.common.utils.AppInfoUtils;
import com.changhong.system.domain.*;
import com.changhong.system.repository.AppDao;
import com.sun.deploy.ui.AppInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-7-31
 * Time: 下午1:28
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, InitializingBean {

    private static final Log logger = LogFactory.getLog(DocumentServiceImpl.class);

    @Autowired
    private AppDao appDao;

    @Value("${application.upload.file.path}")
    private String baseStorePath;

    public void afterPropertiesSet() throws Exception {
        Assert.hasText(baseStorePath, "the basic store path not configure");
    }

    /*********************************上传appCategory:图标/海报/APK文件*************************************************/
    public void uploadAppIconData(MarketApp app) {
        File directory = new File(baseStorePath + app.getAppKey());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //save icon
        AppIcon icon = app.getAppIcon();
        File iconFile = new File(directory, icon.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(iconFile.getAbsolutePath());
            FileCopyUtils.copy(icon.getFile().getInputStream(), dataOut);

            logger.info("finish upload app icon file for" + app.getAppName());
        } catch (Exception e) {
            logger.error(e);
            throw new CHDocumentOperationException("exception app icon failed for app " + app.getAppName(), e);
        }
    }

    public String uploadAppApkData(MarketApp app) {
        File directory = new File(baseStorePath + app.getAppKey());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //save apk
        AppFile apk = app.getAppFile();
        File apkFile = new File(directory, apk.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(apkFile.getAbsolutePath());
            FileCopyUtils.copy(apk.getFile().getInputStream(), dataOut);

            Map<String, String> apkInfo = AppInfoUtils.obtainApkInfo(apkFile.getAbsolutePath(), false);
            boolean packageDuplicate = appDao.loadAppPackageDuplicate(app.getId(), apkInfo.get("packageName"));
            if (packageDuplicate) {
                logger.info("app package is duplicate for " + app.getAppPackage());
                apkFile.delete();
                directory.delete();
                return null;
            }

            if (app.getId() > 0) {
                AppChangeDetails details = DomainHelper.generateNewApkFileChangeDetails(apk.getFile(), app.getAppVersion(), apkInfo.get("versionName"));
                app.getHistory().addChangeDetails(details);
            }

            logger.info("finish upload app apk file for" + app.getAppName());
            app.setAppSize(String.valueOf(apk.getFile().getSize()));
            app.setAppVersionInt(Integer.valueOf(apkInfo.get("versionCode")));
            app.setAppVersion(apkInfo.get("versionName"));
            app.setAppPackage(apkInfo.get("packageName"));
            return app.getAppPackage();
        } catch (IOException e) {
            logger.error(e);
            apkFile.delete();
            directory.delete();
            throw new CHDocumentOperationException("exception app apk failed for app " + app.getAppName(), e);
        }
    }

    public void uploadAppPosterData(MarketApp app) {
        File directory = new File(baseStorePath + app.getAppKey());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //save poster
        AppPoster poster = app.getAppPoster();
        File posterFile = new File(directory, poster.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(posterFile.getAbsolutePath());
            FileCopyUtils.copy(poster.getFile().getInputStream(), dataOut);

            logger.info("finish upload app poster file for" + app.getAppName());
        } catch (Exception e) {
            logger.error(e);
            throw new CHDocumentOperationException("exception poster icon failed for app " + app.getAppName(), e);
        }
    }


     /*********************************管理app专题信息*************************************************/

    public void uploadCategoryIconData(CategoryIcon icon) {
        File directory = new File(baseStorePath + "category");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //save icon
        File iconFile = new File(directory, icon.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(iconFile.getAbsolutePath());
            FileCopyUtils.copy(icon.getFile().getInputStream(), dataOut);

            logger.info("finish upload category icon file");
        } catch (Exception e) {
            logger.error(e);
            throw new CHDocumentOperationException("exception poster icon failed for category", e);
        }
    }

    public void deleteCategoryIconData(String filename) {
        File directory = new File(baseStorePath + "category");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //delete icon
        File iconFile = new File(directory, filename);
        iconFile.deleteOnExit();
    }


    public void uploadTopicIconData(CategoryIcon icon) {
        File directory = new File(baseStorePath + "topic");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //save icon
        File iconFile = new File(directory, icon.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(iconFile.getAbsolutePath());
            FileCopyUtils.copy(icon.getFile().getInputStream(), dataOut);

            logger.info("finish upload category icon file");
        } catch (Exception e) {
            logger.error(e);
            throw new CHDocumentOperationException("exception poster icon failed for topic", e);
        }
    }

    public void deleteTopicIconData(String filename) {
        File directory = new File(baseStorePath + "topic");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //delete icon
        File iconFile = new File(directory, filename);
        iconFile.deleteOnExit();
    }

    public Map<String, String> saveApkParserFileToFS(MultipartFile apkParserFlie) {
        Map<String, String> model = new HashMap<String, String>();

        try {
            if (apkParserFlie != null && apkParserFlie.getSize() > 0) {
                //get apk file name
                String ApkFilename = apkParserFlie.getOriginalFilename();
                File directory = new File(baseStorePath, "parser");
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File file = new File(directory, ApkFilename);
                OutputStream dataOut = new FileOutputStream(file.getAbsolutePath());
                FileCopyUtils.copy(apkParserFlie.getInputStream(), dataOut);

                model = AppInfoUtils.obtainApkInfo(file.getAbsolutePath(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
