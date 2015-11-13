package com.changhong.common.utils;

import android.content.res.AXmlResourceParser;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * User: Jack Wang
 * Date: 15-10-14
 * Time: 下午4:01
 */
public class AppInfoUtils {

    public static Map<String, String> obtainApkInfo(String filepath, boolean shouldDelete) {
        Map<String, String> model = new HashMap<String, String>();

        ZipFile zipFile = null;
        try {
            //Opens a apk file
            zipFile = new ZipFile(filepath);
            //把apk文件插入到"AndroidManifest.xml"中
            ZipEntry zipEntry = zipFile.getEntry("AndroidManifest.xml");
            if (zipEntry != null) {
                //创建解析器
                AXmlResourceParser parser = new AXmlResourceParser();
                //打开待解析的输入流
                parser.open(zipFile.getInputStream(zipEntry));
                while (true) {
                    int type = parser.next();
                    //END_DOCUMENT = 1,XML文档的结束
                    if (type == XmlPullParser.END_DOCUMENT) {
                        break;
                    }
                    switch (type) {
                        //START_TAG = 2,标签的开始
                        case XmlPullParser.START_TAG:
                            for (int k = 0; k < parser.getAttributeCount(); k++) {
                                String attributeName = parser.getAttributeName(k);
                                //System.out.println(attributeName + ":" + parser.getAttributeValue(k));

                                if ("versionCode".equals(attributeName)) {
                                    model.put("versionCode", parser.getAttributeUnsignedIntValue(k, 0) + "");
                                }
                                if ("versionName".equals(attributeName)) {
                                    model.put("versionName", parser.getAttributeValue(k));
                                }
                                if ("package".equals(attributeName)) {
                                    model.put("packageName", parser.getAttributeValue(k));
                                }
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        try {
            File file = new File(filepath);
            String fileSize = file.length() + "";
            model.put("fileSize", getFileSize(fileSize));

            if (shouldDelete && file.exists()) {
                boolean deleted = file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }


    private static String getFileSize(String size) {
        double howManyMByte = Double.valueOf(size) / 1024 / 1024;
        return String.format("%.2f", howManyMByte);
    }

    public static void main(String[] args) {
        Map<String, String> map = AppInfoUtils.obtainApkInfo("D:\\softwareManage\\Andriod\\tomcat_static\\webapps\\appmarket\\upload\\test.apk", false);
        System.out.println(map);
    }
}
