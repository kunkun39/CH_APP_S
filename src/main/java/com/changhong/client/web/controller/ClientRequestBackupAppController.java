package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-10-24
 * Time: 下午8:10
 * To change this template use File | Settings | File Templates.
 */
public class ClientRequestBackupAppController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String boxMac = ServletRequestUtils.getStringParameter(request, "boxMac");
        int[] appIds = ServletRequestUtils.getIntParameters(request,"appIds");

        String responseJSON = "";
        if(boxMac != null && (appIds != null && appIds.length > 0)) {
            responseJSON = clientService.requestBackupApps(appIds, boxMac);
        }

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();
        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
