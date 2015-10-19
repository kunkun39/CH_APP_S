package com.changhong.system.web.controller;

import com.changhong.system.domain.BoxRecommendRecord;
import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import com.changhong.system.web.facade.dto.BoxRecommendDTO;
import org.joda.time.LocalDate;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.changhong.common.utils.*;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:07
 */
public class DashboardController extends AbstractController {

    private AppService appService;

    private String fileRequestHost;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<AppCategoryDTO> categories = appService.obtainAllFirstLevelCategory(true);
        model.put("categories", categories);

        for (int i = 0; i < 4; i++) {
            AppCategoryDTO dto = categories.get(i);
            model.put("category_" + (i + 1), dto);
        }

        List<BoxRecommendDTO> recommends = appService.obtainAllBoxRecommends();
        model.put("recommends", recommends);

        BoxRecommendRecord record = appService.obtainBoxRecommendRecord();
        model.put("record", record);
        int currentUserId = SecurityUtils.currectUserId();
        model.put("currentUserId", currentUserId);

        model.put("fileRequestHost", fileRequestHost);
        return new ModelAndView("backend/dashboard", model);
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }
}
