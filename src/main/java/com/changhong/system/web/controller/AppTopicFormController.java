package com.changhong.system.web.controller;

import com.changhong.system.domain.AppTopic;
import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import com.changhong.system.web.facade.dto.AppTopicDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午11:10
 */
public class AppTopicFormController extends AbstractController {

    private AppService appService;

    private String fileRequestHost;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");
        int topicId = ServletRequestUtils.getIntParameter(request, "topicId", -1);
        String topicName = ServletRequestUtils.getStringParameter(request, "topicName", "");

        if ("save".equals(method)) {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile iconFile = multipartRequest.getFile("topicIconName");

            appService.saveOrUpdateAppTopic(topicId, topicName, iconFile);
            return new ModelAndView(new RedirectView("apptopicoverview.html"));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            AppTopicDTO dto = null;
            if ("add".equals(method)) {
                dto = new AppTopicDTO();
            } else {
                dto = appService.obtainAppTopicById(topicId);
            }
            model.put("topic", dto);

            model.put("fileRequestHost", fileRequestHost);
            return new ModelAndView("backend/app/apptopicform", model);
        }

    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }
}
