package com.changhong.system.web.controller;

import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppChangeHistoryDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午4:55
 */
public class AppHistoryManagementController extends AbstractController {

    private AppService appService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int appId = ServletRequestUtils.getIntParameter(request, "appId", -1);

        Map<String, Object> model = new HashMap<String, Object>();
        List<AppChangeHistoryDTO> histories = appService.obtainAppChangeHistories(appId);
        model.put("histories", histories);
        return new ModelAndView("backend/app/apphistory", model);
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
}
