package com.changhong.system.web.controller;

import com.changhong.system.service.AppService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午11:10
 */
public class AppTopicDeleteController extends AbstractController {

    private AppService appService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int topicId = ServletRequestUtils.getIntParameter(request, "topicId", -1);

        appService.deleteAppTopic(topicId);
        return new ModelAndView(new RedirectView("apptopicoverview.html"));
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
}
