package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午10:14
 */
public class ClientAppDetailsRecommendController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
        String responseJSON = "";
        if (categoryId > 0) {
            responseJSON = clientService.obtainAppDetailsRecommend(categoryId);
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
