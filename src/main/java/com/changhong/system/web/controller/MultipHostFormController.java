package com.changhong.system.web.controller;

import com.changhong.system.domain.MultipHost;
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
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午11:10
 */
public class MultipHostFormController extends AbstractController {

    private SystemService systemService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");
        int hostId = ServletRequestUtils.getIntParameter(request, "hostId", -1);
        String hostName = ServletRequestUtils.getStringParameter(request, "hostName", "");

        if ("save".equals(method)) {
            systemService.saveOrUpdateMultipHost(hostId, hostName);
            return new ModelAndView(new RedirectView("hostoverview.html"));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            MultipHost host = null;
            if (hostId <= 0) {
                host = new MultipHost();
            } else {
                host = systemService.obtainMultipHostById(hostId);
            }
            model.put("host", host);

            return new ModelAndView("backend/system/hostform", model);
        }
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
