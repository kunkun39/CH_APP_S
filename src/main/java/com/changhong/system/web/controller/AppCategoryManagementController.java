package com.changhong.system.web.controller;

import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-7-29
 * Time: 下午5:40
 */
public class AppCategoryManagementController extends AbstractController {

    private AppService appService;

    private String fileRequestHost;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        List<AppCategoryDTO> categories = appService.obtainAllFirstLevelCategory(true);
        model.put("categories", categories);

        model.put("fileRequestHost", fileRequestHost);
        return new ModelAndView("backend/app/appcategorymanage", model);
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }
}
