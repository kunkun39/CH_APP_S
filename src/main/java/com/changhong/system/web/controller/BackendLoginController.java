package com.changhong.system.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:33
 */
public class BackendLoginController extends AbstractController {

    private String projectVersion;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("projectVersion", projectVersion);

        return new ModelAndView("backend/index", model);
    }


    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }
}
