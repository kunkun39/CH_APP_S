package com.changhong.system.web.controller;

import com.changhong.system.service.AppService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 15-9-21
 * Time: 下午2:22
 */
public class BoxRecommendCancelController extends AbstractController {

    private AppService appService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        appService.cancelBoxRecommend();

        return new ModelAndView(new RedirectView("dashboard.html"));
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
}
