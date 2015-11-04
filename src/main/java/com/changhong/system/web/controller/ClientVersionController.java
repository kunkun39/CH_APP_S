package com.changhong.system.web.controller;

import com.changhong.common.utils.CHFileUtils;
import com.changhong.system.domain.ClientVersion;
import com.changhong.system.service.SystemService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:27
 */
public class ClientVersionController extends AbstractController {

    private SystemService systemService;

    private String clintApkRequestHost;

    private String baseStorePath;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method", "load");

        if ("load".equals(method)) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clintApkRequestHost", clintApkRequestHost);

            ClientVersion version = systemService.obtainClientVersion();
            model.put("version", version);

            boolean apkFileExist = false;
            File file = new File(baseStorePath, ClientVersion.APK_FILE_NAME);
            if (file.exists()) {
                apkFileExist = true;
            }
            model.put("apkFileExist", apkFileExist);

            return new ModelAndView("/backend/system/clientversion", model);
        } else if("add".equals(method)) {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile clientApkUploadFile = multipartRequest.getFile("clientApkUploadFile");
            int version = ServletRequestUtils.getIntParameter(request, "clientVersion", 1);

            systemService.saveClientVersion(version, clientApkUploadFile);
            return new ModelAndView(new RedirectView("clientversionshow.html?method=load"));

        } else {
            boolean beginUpdate = ServletRequestUtils.getBooleanParameter(request, "beginUpdate", false);

            systemService.changeClientVersionStatus(beginUpdate);
            return new ModelAndView(new RedirectView("clientversionshow.html?method=load"));
        }
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public void setClintApkRequestHost(String clintApkRequestHost) {
        this.clintApkRequestHost = clintApkRequestHost;
    }

    public void setBaseStorePath(String baseStorePath) {
        this.baseStorePath = baseStorePath;
    }
}

