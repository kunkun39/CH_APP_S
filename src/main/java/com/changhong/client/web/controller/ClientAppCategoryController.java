package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
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
public class ClientAppCategoryController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
         //拿到接口数据
        String responseJSON = clientService.obtainAllAppCategoryInfo();

        //返回结果
        //响应的MIME类型和编码方式
        response.setContentType("application/json; charset=utf-8");

        //获取输出流对象writer，通过它可以把输出发送到客户端
        PrintWriter writer = response.getWriter();

        //输出流对象把responseJSON数据发送给前台
        writer.write(responseJSON);

        //从缓冲中发送
        writer.flush();

        //关闭输出流,数据发送完毕
        writer.close();

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
