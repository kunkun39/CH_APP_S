package com.changhong.system.web.controller;


import com.changhong.common.utils.AppInfoUtils;
import com.changhong.system.service.DocumentService;
import com.changhong.system.service.SystemService;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dangwei
 * Date: 15-11-11
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class ApkOnlineParserController extends AbstractController {

    private DocumentService documentService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method", "load");

        if ("load".equals(method)) {
            return new ModelAndView("backend/system/clientapkparser");
        } else {
            //从页面选择框获得apk文件
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile clientApkParserFlie = multipartRequest.getFile("clientApkParserFlie");

            //apk保存到fs
            Map<String, String> model = documentService.saveApkParserFileToFS(clientApkParserFlie);
            return new ModelAndView("backend/system/clientapkparser", model);
        }
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


}
