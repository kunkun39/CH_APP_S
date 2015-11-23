package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: dangwei
 * Date: 15-10-21
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class ClientBootAdvertiseController extends AbstractController{

      private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得开机图片的JSPON数据
        String responseJSON = clientService.obtainBootImage();

        /*****************返回结果******************/
        response.setContentType("application/json; charset=utf-8");
        //获得输出流对象
        PrintWriter writer = response.getWriter();
        //把输出流发送到页面URL上
        writer.write(responseJSON);
        //刷新缓冲
        writer.flush();
        //关闭数据发送
        writer.close();

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
