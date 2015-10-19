package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 15-8-14
 * Time: 下午2:25
 */
public class ClientAppDownloadController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int appId = ServletRequestUtils.getIntParameter(request, "appId", -1);
        String boxMac = ServletRequestUtils.getStringParameter(request, "boxMac", "-1");

        if (appId > 0) {
            clientService.updateAppDownloadTime(appId, boxMac);
        }

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
