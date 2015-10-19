package com.changhong.common.utils;

import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 15-8-6
 * Time: 上午10:22
 */
public class CHFileUtils {

    public static boolean isImageFile(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return false;
        }
        fileName = fileName.toUpperCase();
        if (fileName.endsWith(".PNG") || fileName.endsWith(".JEPG") || fileName.endsWith(".JPG")) {
            return true;
        }
        return false;
    }

    public static boolean isAndroidAppFile(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return false;
        }
        fileName = fileName.toUpperCase();
        if (fileName.endsWith(".APK")) {
            return true;
        }
        return false;
    }
}
