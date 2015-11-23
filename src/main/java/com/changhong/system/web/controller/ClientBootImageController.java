package com.changhong.system.web.controller;


import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.ClientBootImage;
import com.changhong.system.service.SystemService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dangwei
 * Date: 15-10-8
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class ClientBootImageController extends AbstractController {

    private final static int MAX_BOOT_IMAGE_SIZE = 1024 * 512;

    private SystemService systemService;

    private String clientImageRequestHost;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method", "load");

        /*把在页面上要显示的数据集合到model中*/
        if ("load".equals(method)) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clientImageRequestHost", clientImageRequestHost);

            boolean error = ServletRequestUtils.getBooleanParameter(request, "error", false);
            model.put("error", error);

            //从数据库中获取图片
            ClientBootImage clientbootimage = systemService.obtainClientBootImage();
            model.put("clientbootimage", clientbootimage);

            //把页面上需要加载和引用的数据都封装起来，便于页面上使用
            return new ModelAndView("backend/system/clientbootimage", model);
        } else {
            //从页面上获取图片文件
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            // "clientImageUploadFile"为input标签的"name"
            MultipartFile clientImageUploadFile = multipartRequest.getFile("clientImageUploadFile");

            //check the file size
            if (clientImageUploadFile.getSize() > MAX_BOOT_IMAGE_SIZE) {
                return new ModelAndView(new RedirectView("clientbootimageshow.html?method=load&error=true"));
            }

            //获得图片原始文件名
            String uploadFileName = clientImageUploadFile != null ? clientImageUploadFile.getOriginalFilename() : "";

            /*保存:文件名保存到数据库中，文件保存到文件系统中*/
            systemService.saveClientBootImage(uploadFileName, clientImageUploadFile);

            //只负责跳转到页面，其他不做任何工作
            return new ModelAndView(new RedirectView("clientbootimageshow.html?method=load"));
        }
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public void setClientImageRequestHost(String clientImageRequestHost) {
        this.clientImageRequestHost = clientImageRequestHost;
    }
}
