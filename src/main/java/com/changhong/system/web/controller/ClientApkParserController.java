package com.changhong.system.web.controller;


import com.changhong.common.utils.AppInfoUtils;
import com.changhong.system.service.SystemService;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.AbstractController;
import com.changhong.common.utils.CHStringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-11-11
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class ClientApkParserController extends AbstractController {

    private SystemService systemService;

    private String baseStorePath;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String method = ServletRequestUtils.getStringParameter(request, "method", "load");

        if("load".equals(method)){
            return new ModelAndView("backend/system/clientapkparser");
        }else {
              //从页面选择框获得apk文件
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile clientApkParserFlie = multipartRequest.getFile("clientApkParserFlie");

            //apk保存到fs
            systemService.saveApkParserFileToFS(clientApkParserFlie);

            //访问apk路径,直接从文件系统中获得
            String direction = baseStorePath + clientApkParserFlie.getOriginalFilename();

            //解析apk文件信息
           // Map<String, String> model = AppInfoUtils.obtainApkInfo("D:\\softwareManage\\Andriod\\tomcat_static\\webapps\\appmarket\\upload\\test.apk");
            Map<String, String> model = AppInfoUtils.obtainApkInfo(direction);

            return new ModelAndView("backend/system/clientapkparser",model);
        }



    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

     public void setBaseStorePath(String baseStorePath) {
        this.baseStorePath = baseStorePath;
    }
}
