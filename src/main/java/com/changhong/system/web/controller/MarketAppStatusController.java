package com.changhong.system.web.controller;

import com.changhong.common.exception.CHApplicationException;
import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import com.changhong.system.web.facade.dto.MarketAppDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:42
 */
public class MarketAppStatusController extends AbstractController {

    private AppService appService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String appName = ServletRequestUtils.getStringParameter(request, "appName", "");
        String appStatus = ServletRequestUtils.getStringParameter(request, "appStatus", "ALL");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);

        int marketAppId = ServletRequestUtils.getIntParameter(request, "marketAppId", -1);
        String resetStatus = ServletRequestUtils.getStringParameter(request, "resetStatus", "");

        if (!StringUtils.hasText(resetStatus)) {
            throw new CHApplicationException("status can't be empty");
        }

        appService.changeMarketAppStatus(marketAppId, resetStatus);

        return new ModelAndView(new RedirectView("marketappoverview.html?current=" + current + "&appName=" + appName + "&appStatus=" + appStatus + "&categoryId=" + categoryId));

    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
}
