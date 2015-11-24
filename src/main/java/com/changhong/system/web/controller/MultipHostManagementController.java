package com.changhong.system.web.controller;

import com.changhong.system.domain.MultipHost;
import com.changhong.system.service.SystemService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-11-23
 * Time: 上午10:21
 */
public class MultipHostManagementController extends AbstractController {

    private SystemService systemService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        List<MultipHost> hosts = systemService.obtainAllMultipHosts();
        model.put("hosts", hosts);

        return new ModelAndView("backend/system/hostoverview", model);
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
