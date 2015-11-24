package com.changhong.system.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: dangwei
 * Date: 15-11-18
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class ApkUploadProcessController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int percentage = 0;
        try {
            percentage = (Integer) request.getSession().getAttribute("UPLAOD_FILE_PROCESS");
            //maybe the list of process has not begin and this request already launched
        } catch (Exception e) {
        }
        JSONObject o = new JSONObject();
        o.put("percentage", percentage + "");

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(o.toJSONString());
        writer.flush();
        writer.close();
        return null;
    }

}
