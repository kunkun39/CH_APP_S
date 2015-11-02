package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import com.changhong.common.utils.DesUtils;
import org.springframework.util.StringUtils;
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
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
public class ClientDeleteAppBackupController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String boxMac = ServletRequestUtils.getStringParameter(request, "boxMac");
        String appIds = ServletRequestUtils.getStringParameter(request,"appIds");

        String responseJSON = "";
        if(StringUtils.hasText(boxMac) && StringUtils.hasText(appIds)) {
            boxMac = DesUtils.getDesString(boxMac);
            responseJSON = clientService.deleteBackupApps(appIds, boxMac);
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
