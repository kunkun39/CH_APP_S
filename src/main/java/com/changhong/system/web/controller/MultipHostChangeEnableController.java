package com.changhong.system.web.controller;

import com.changhong.system.service.SystemService;
import com.changhong.system.service.UserService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 上午9:01
 */
public class MultipHostChangeEnableController extends AbstractController {

    private SystemService systemService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = ServletRequestUtils.getIntParameter(request, "hostId", -1);

        systemService.changeStatusForHost(hostId);

        return new ModelAndView(new RedirectView("hostoverview.html"));
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
