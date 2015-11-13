package com.changhong.system.service;

import com.changhong.client.service.CacheService;
import com.changhong.common.exception.CHDocumentOperationException;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.ClientBootImage;
import com.changhong.system.domain.ClientVersion;
import com.changhong.system.repository.SystemDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:28
 */
//@Service("systemService")
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    private static final Log logger = LogFactory.getLog(SystemServiceImpl.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SystemDao systemDao;

    @Value("${application.upload.file.path}")
    private String baseStorePath;

    public ClientVersion obtainClientVersion() {
        return systemDao.findClientVersion();
    }

    public void saveClientVersion(int clientVersion, MultipartFile clientApkUploadFile) {
        if (clientApkUploadFile != null && clientApkUploadFile.getSize() > 0) {
            //save file
            File file = new File(baseStorePath, ClientVersion.APK_FILE_NAME);
            if (file.exists()) {
                file.delete();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                OutputStream dataOut = new FileOutputStream(file.getAbsolutePath());
                FileCopyUtils.copy(clientApkUploadFile.getInputStream(), dataOut);

                logger.info("finish upload client apk file");
            } catch (Exception e) {
                logger.error(e);
                throw new CHDocumentOperationException("exception upload client apk file");
            }

            //save domain
            ClientVersion version = systemDao.findClientVersion();
            version.setClientVersion(clientVersion);
            systemDao.saveClientVersion(version);

            //update cache
            cacheService.setCurrentClientVersion(clientVersion);
        }
    }

    public void changeClientVersionStatus(boolean beginUpdate) {
        //save domain
        ClientVersion version = systemDao.findClientVersion();
        version.setBeginUpdate(beginUpdate);
        systemDao.saveClientVersion(version);

        //update cache
        cacheService.setClientBeginUpdate(beginUpdate);
    }

    /*获得图片文件*/
    public ClientBootImage obtainClientBootImage() {
        return (ClientBootImage) systemDao.findById(1, ClientBootImage.class);
    }

    /*把图片文件包copy到服务器文件系统,图片文件名保存到数据库*/
    public void saveClientBootImage(String uploadFileName, MultipartFile clientImageUploadFile) {
        if (clientImageUploadFile != null && clientImageUploadFile.getSize() > 0) {
            ClientBootImage bootImage = (ClientBootImage) systemDao.findById(1, ClientBootImage.class);

            //图片原文件名重新命名，避免重复
            String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
            String actualFileName = CHStringUtils.getRandomString(10) + "." + suffix;

            /*把文件copy到文件系统*/
            File oldFile = new File(baseStorePath, bootImage.getActualFileName());
            if (oldFile.exists()) {
                oldFile.delete();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            File newFile = new File(baseStorePath, actualFileName);
            try {
                OutputStream dataOut = new FileOutputStream(newFile.getAbsolutePath());
                FileCopyUtils.copy(clientImageUploadFile.getInputStream(), dataOut);

                logger.info("finish upload client boot image file");
            } catch (Exception e) {
                logger.error(e);
                throw new CHDocumentOperationException("exception upload client boot image file");
            }

            //save domain:把文件名保存到数据库
            bootImage.setId(1);
            bootImage.setActualFileName(actualFileName);
            systemDao.saveOrUpdate(bootImage);

            //reset che cache
            cacheService.setBootImageFileName(actualFileName);
        }
    }

    /*保存待解析的apk文件到fs中*/

    public void saveApkParserFileToFS(MultipartFile clientApkParserFlie){
            if (clientApkParserFlie != null && clientApkParserFlie.getSize() > 0){
                    //get apk file name
                    String ApkFilename = clientApkParserFlie.getOriginalFilename();
                    File file = new File(baseStorePath, ApkFilename);
//                    if (file.exists()) {
//                        file.delete();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    try {
                        OutputStream dataOut = new FileOutputStream(file.getAbsolutePath());
                        FileCopyUtils.copy(clientApkParserFlie.getInputStream(), dataOut);

                        logger.info("finish upload client apk parser file");
                    } catch (Exception e) {
                        logger.error(e);
                        throw new CHDocumentOperationException("exception upload client apk parser file");
                    }
            }
    }
}
