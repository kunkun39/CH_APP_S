package com.changhong.system.service;

import com.changhong.system.domain.ClientBootImage;
import com.changhong.system.domain.ClientVersion;
import com.changhong.system.domain.MultipHost;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:28
 */
public interface SystemService {

    /*client version**********************************************************************/

    ClientVersion obtainClientVersion();

    void saveClientVersion(int clientVersion, MultipartFile clientApkUploadFile);

    void changeClientVersionStatus(boolean beginUpdate);

    /*boot image**********************************************************************/

    ClientBootImage obtainClientBootImage();

    void saveClientBootImage(String uploadFileName, MultipartFile clientImageUploadFile);

    /*multip host**********************************************************************/

    List<MultipHost> obtainAllMultipHosts();

    void changeStatusForHost(int hostId);

    void deleteMultipHost(int hostId);

    void saveOrUpdateMultipHost(int hostId, String hostName);

    MultipHost obtainMultipHostById(int hostId);
}
