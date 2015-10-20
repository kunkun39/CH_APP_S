package com.changhong.system.service;

import com.changhong.system.domain.ClientBootImage;
import com.changhong.system.domain.ClientVersion;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:28
 */
public interface SystemService {

    ClientVersion obtainClientVersion();

    void saveClientVersion(int clientVersion, MultipartFile clientApkUploadFile);

    ClientBootImage obtainClientBootImage();

    void saveClientBootImage(String uploadFileName,MultipartFile clientImageUploadFile);
}
